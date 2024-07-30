Feature: Review - Create, Update, Get by Id, Get All, Delete
  Background:
    * def company_url = host + config.uri.company

  @ReviewService
  @CreatingReview
  Scenario: Creating a Review
    * def companyResponse = call read('Company.feature@CreateCompany')
    And match companyResponse.response.id == '#notnull'
    * def companyId = companyResponse.response.id
    * def reviewResponse = call read('Review.feature@CreateReview') { companyId: '#(companyId)'}
    * def reviewId = reviewResponse.response.id
    * call read('Review.feature@DeleteReview') { companyId: '#(companyId)', id: '#(reviewId)'}
    * call read('Company.feature@DeleteCompany') { id: '#(companyId)'}

  @ReviewService
  @UpdatingReview
  Scenario: Updating a Review
    * def companyCreateResponse = call read('Company.feature@CreateCompany')
    And match companyCreateResponse.response.id == '#notnull'
    * def companyId = companyCreateResponse.response.id
    * def reviewResponse = call read('Review.feature@CreateReview') { companyId: '#(companyId)'}
    * def reviewId = reviewResponse.response.id
    * call read('Review.feature@UpdateReview') { companyId: '#(companyId)', id: '#(reviewId)', title: 'Review title changed', description: 'Review description changed', rating: 4.5}
    * def reviewGetResponse = call read('Review.feature@GetReviewById') { companyId: '#(companyId)', id: '#(reviewId)' }
    And match reviewGetResponse.response.title == 'Review title changed'
    And match reviewGetResponse.response.description == 'Review description changed'
    And match reviewGetResponse.response.rating == 4.5
    * call read('Review.feature@DeleteReview') { companyId: '#(companyId)', id: '#(reviewId)'}
    * call read('Company.feature@DeleteCompany') { id: '#(companyId)'}

  @ReviewService
  @GettingReview
  Scenario: Getting a Review
    * def companyCreateResponse = call read('Company.feature@CreateCompany')
    And match companyCreateResponse.response.id == '#notnull'
    * def companyId = companyCreateResponse.response.id
    * def reviewResponse = call read('Review.feature@CreateReview') { companyId: '#(companyId)'}
    * def reviewId = reviewResponse.response.id
    * def reviewGetResponse = call read('Review.feature@GetReviewById') { companyId: '#(companyId)', id: '#(reviewId)' }
    * call read('Review.feature@DeleteReview') { companyId: '#(companyId)', id: '#(reviewId)'}
    * call read('Company.feature@DeleteCompany') { id: '#(companyId)'}

  @ReviewService
  @DeletingReview
  Scenario: Deleting a Review
    * def companyCreateResponse = call read('Company.feature@CreateCompany')
    And match companyCreateResponse.response.id == '#notnull'
    * def companyId = companyCreateResponse.response.id
    * def reviewResponse = call read('Review.feature@CreateReview') { companyId: '#(companyId)'}
    * def reviewId = reviewResponse.response.id
    * call read('Review.feature@DeleteReview') { companyId: '#(companyId)', id: '#(reviewId)'}
    * def companyGetResponse = call read('Company.feature@GetReviewByIdNotFound') { companyId: '#(companyId)', id: '#(reviewId)' }
    * call read('Company.feature@DeleteCompany') { id: '#(companyId)'}

  @ReviewService
  @GetReviewList
  Scenario: Get Review List
    * def companyCreateResponse = call read('Company.feature@CreateCompany')
    And match companyCreateResponse.response.id == '#notnull'
    * def companyId = companyCreateResponse.response.id
    * def reviewResponse = call read('Review.feature@CreateReview') { companyId: '#(companyId)'}
    * def reviewId = reviewResponse.response.id
    Given url company_url + '/' + companyId + '/reviews'
    When method GET
    Then status 200
    And match each response.[*] == '#notnull'
    And match each response.[*] == '#notnull'
    And match each response.[*] contains deep {id: '#number'}
    And match each response.[*] contains deep {title: '#string'}
    And match each response.[*] contains deep {description: '#string'}
    And match each response.[*] contains deep {rating: '#number'}
    * call read('Review.feature@DeleteReview') { companyId: '#(companyId)', id: '#(reviewId)'}
    * call read('Company.feature@DeleteCompany') { id: '#(companyId)'}

  @CreateReview
  @Ignore
  Scenario: Insert Review
    * def req = read('data/review_request.json')
    * req.title = config.params.review.title
    * req.description = config.params.review.description
    * req.rating = config.params.review.rating
    Given url company_url +  '/' + companyId + '/reviews'
    And request req
    When method POST
    Then status 201

  @CreateReviewWithParams
  @Ignore
  Scenario: Insert Review
    * def req = read('data/review_request.json')
    * req.title = title
    * req.description = description
    * req.rating = rating
    Given url company_url +  '/' + companyId + '/reviews'
    And request req
    When method POST
    Then status 201

  @GetReviewByIdNotFound
  @Ignore
  Scenario: Get Review By Id
    Given url company_url + '/' + companyId + '/reviews/' + id
    When method GET
    Then status 404
    And match response == '#notnull'
    And match response == '#notnull'
    And match response contains deep {code: '#number'}
    And match response contains deep {status: '#string'}
    And match response contains deep {description: '#string'}

  @GetReviewById
  @Ignore
  Scenario: Get Review By Id
    Given url company_url + '/' + companyId + '/reviews/' + id
    When method GET
    Then status 200
    And match response == '#notnull'
    And match response contains deep {company: {id: '#number'}}
    And match response contains deep {id: '#number'}
    And match response contains deep {title: '#string'}
    And match response contains deep {description: '#string'}
    And match response contains deep {rating: '#number'}

  @UpdateReview
  @Ignore
  Scenario: Update Review
    * def req = read('data/review_request.json')
    * req.title = title
    * req.description = description
    * req.rating = rating
    Given url company_url + '/' + companyId + '/reviews/' + id
    And request req
    When method PUT
    Then status 200

  @DeleteReview
  @Ignore
  Scenario: Delete Review
    Given url company_url + '/' + companyId + '/reviews/' + id
    When method DELETE
    Then status 204