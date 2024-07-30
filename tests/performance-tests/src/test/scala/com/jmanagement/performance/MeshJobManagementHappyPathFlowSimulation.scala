package com.jmanagement.performance

import io.gatling.core.Predef.{Simulation, _}
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class MeshJobManagementHappyPathFlowSimulation extends Simulation {

  val httpConf: HttpProtocolBuilder = http.baseUrl("http://localhost:9094/v1")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json");

  val concurrentUsers: Int = System.getProperty("concurrentUsers").toInt;
  val lengthOfTest: Int = System.getProperty("lengthOfTest").toInt;

  def createCompany(): ChainBuilder = {
    exec(
      http("Create Company")
        .post("/companies")
        .body(PebbleFileBody("company_request.json"))
        .check(jsonPath("$.id").saveAs("companyId"))
        .check(status.is(201))
    )
  }

  def updateCompany(): ChainBuilder = {
    val url = "/companies/" + s"#{companyId}"
    exec(
      http("Update Company")
        .put(url)
        .body(PebbleFileBody("company_request.json"))
        .check(status.is(200))
    )
  }

  def createJob(): ChainBuilder = {
    val url = "/companies/" + s"#{companyId}" + "/jobs"
    exec(
      http("Create Job")
        .post(url)
        .body(PebbleFileBody("job_request.json"))
        .check(jsonPath("$.id").saveAs("jobId"))
        .check(status.is(201))
    )
  }

  def updateJob(): ChainBuilder = {
    val url = "/companies/" + s"#{companyId}" + "/jobs/" + s"#{jobId}"
    exec(
      http("Update Job")
        .put(url)
        .body(PebbleFileBody("job_request.json"))
        .check(status.is(200))
    )
  }

  def createReview: ChainBuilder = {
    val url = "/companies/" + s"#{companyId}" + "/reviews"
    exec(
      http("Create Review")
        .post(url)
        .body(PebbleFileBody("review_request.json"))
        .check(jsonPath("$.id").saveAs("reviewId"))
        .check(status.is(201))
    )
  }

  def updateReview(): ChainBuilder = {
    val url = "/companies/" + s"#{companyId}" + "/reviews/" + s"#{reviewId}"
    exec(
      http("Update Review")
        .put(url)
        .body(PebbleFileBody("review_request.json"))
        .check(status.is(200))
    )
  }

  def getJobsByCompanyId: ChainBuilder = {
    val url = "/companies/" + s"#{companyId}" + "/jobs"
    exec(
      http("Get jobs by company id")
        .get(url)
        .check(status.is(200))
    )
  }

  def getJobById: ChainBuilder = {
    val url = "/companies/" + s"#{companyId}" + "/jobs/" + s"#{jobId}"
    exec(
      http("Get job by id")
        .get(url)
        .check(status.is(200))
    )
  }

  def getReviewById: ChainBuilder = {
    val url = "/companies/" + s"#{companyId}" + "/reviews/" + s"#{reviewId}"
    exec(
      http("Get review by id")
        .get(url)
        .check(status.is(200))
    )
  }

  def getReviewsByCompanyId: ChainBuilder = {
    val url = "/companies/" + s"#{companyId}" + "/reviews"
    exec(
      http("Get reviews by company id")
        .get(url)
        .check(status.is(200))
    )
  }

  def getAllCompanies: ChainBuilder = {
    exec(
      http("Get all companies")
        .get("/companies")
        .check(status.is(200))
    )
  }

  def getCompanyById: ChainBuilder = {
    val url = "/companies/" + s"#{companyId}"
    exec(
      http("Get company by Id")
        .get(url)
        .check(status.is(200))
    )
  }

  def deleteCompanyById(): ChainBuilder = {
    val url = "/companies/" + s"#{companyId}"
    exec(
      http("Delete company by id")
        .delete(url)
        .check(status.is(204))
    )
  }

  def deleteJobById(): ChainBuilder = {
    val url = "/companies/" + s"#{companyId}" + "/jobs/" + s"#{jobId}"
    exec(
      http("Delete job by id")
        .delete(url)
        .check(status.is(204))
    )
  }

  def deleteReviewById(): ChainBuilder = {
    val url = "/companies/" + s"#{companyId}" + "/reviews/" + s"#{reviewId}"
    exec(
      http("Delete review by id")
        .delete(url)
        .check(status.is(204))
    )
  }

  val scn: ScenarioBuilder = scenario("Job Management Simulation")
    .exec(createCompany())
    .exec(createJob())
    .exec(createReview)
    .exec(getAllCompanies)
    .exec(getCompanyById)
    .exec(getJobsByCompanyId)
    .exec(getJobById)
    .exec(getReviewsByCompanyId)
    .exec(getReviewById)
    .exec(updateCompany())
    .exec(updateJob())
    .exec(updateReview())
    .exec(deleteReviewById())
    .exec(deleteJobById())
    .exec(deleteCompanyById())

  setUp(scn.inject(
      rampConcurrentUsers(0) to (concurrentUsers) during (60 seconds),
      constantConcurrentUsers(concurrentUsers) during (lengthOfTest minutes))
    .protocols(httpConf))

}
