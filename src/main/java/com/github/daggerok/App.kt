package com.github.daggerok

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.asType
import org.springframework.data.r2dbc.core.await
import org.springframework.data.r2dbc.core.awaitOneOrNull
import org.springframework.data.r2dbc.repository.query.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

data class Employee(
    var name: String,
    var salary: Int,
    var organizationId: Long,
    @Id var id: Int? = null
)

interface EmployeeRepository : ReactiveCrudRepository<Employee, Long> {
  @Query("select id, name, salary, organization_id from employee e where e.organization_id = $1")
  fun findByOrganizationId(organizationId: Int): Flux<Employee>
}

data class Organization(
    var name: String,
    @Id var id: Int? = null
)

interface OrganizationRepository : ReactiveCrudRepository<Organization, Int>

data class OrganizationDTO(var id: Long?, var name: String) {
  var employees: MutableList<Any> = ArrayList()

  constructor(employees: MutableList<Any>) : this(null, "") {
    this.employees = employees
  }
}

@RestController
@RequestMapping("/employees")
class EmployeeController(private val repository: EmployeeRepository) {

  @RequestMapping
  fun findAll(): Flux<Employee> = repository.findAll()

  @GetMapping("/{id}")
  fun findById(@PathVariable id: Long): Mono<Employee> = repository.findById(id)

  @GetMapping("/organization/{organizationId}")
  fun findByOrganizationId(@PathVariable organizationId: Int): Flux<Employee> = repository.findByOrganizationId(organizationId)

  @PostMapping
  fun add(@RequestBody employee: Employee): Mono<Employee> = repository.save(employee)
}

class CoroutinesRepository(private val databaseClient: DatabaseClient) {
  suspend fun findById(id: Long): Employee? = databaseClient
      .execute()
      .sql("SELECT * FROM employees WHERE id = :name")
      .bind("name", id)
      .asType<Employee>()
      .fetch()
      .awaitOneOrNull()
}

@SpringBootApplication
class App

fun main() {
  runApplication<App> {
    addInitializers(beans {
      bean {
        PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration
                .builder()
                .host("127.0.0.1")
                .port(5432)
                .database("postgres")
                .username("postgres")
                .password("postgres")
                //.schema("schema1")
                .build()
        )
      }
      bean {
        CoroutinesRepository(ref())
      }
    })
  }
}
