/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package v1.services

import support.UnitSpec
import uk.gov.hmrc.domain.Nino
import uk.gov.hmrc.http.HeaderCarrier
import v1.controllers.EndpointLogContext
import v1.mocks.connectors.MockRetrieveSelfEmploymentAdjustmentsConnector
import v1.models.outcomes.ResponseWrapper
import v1.fixtures.selfEmployment.RetrieveSelfEmploymentAdjustmentsFixtures._
import v1.models.request.RetrieveAdjustmentsRequestData
import v1.models.response.retrieveBsasAdjustments.selfEmployment.RetrieveSelfEmploymentAdjustmentsResponse

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class RetrieveSelfEmploymentAdjustmentsServiceSpec extends UnitSpec {


  private val nino = Nino("AA123456A")
  val id = "f2fb30e5-4ab6-4a29-b3c1-c7264259ff1c"
  private val correlationId = "X-123"


  val request = RetrieveAdjustmentsRequestData(nino, id)

  val response = RetrieveSelfEmploymentAdjustmentsResponse(metaDataModel, bsasDetailModel)

  trait Test extends MockRetrieveSelfEmploymentAdjustmentsConnector {

    implicit val hc: HeaderCarrier = HeaderCarrier()
    implicit val logContext: EndpointLogContext = EndpointLogContext("RetrieveSelfEmploymentAdjustmentsController", "RetrieveSelfEmploymentAdjustments")

    val service = new RetrieveSelfEmploymentAdjustmentsService(mockConnector)
  }

  "retrieveSelfEmploymentAdjustments" should {
    "return a valid response" when {
      "a valid request is supplied" in new Test {
        MockRetrieveSelfEmploymentAdjustmentsConnector.retrieveSelfEmploymentAdjustments(request)
          .returns(Future.successful(Right(ResponseWrapper(correlationId, response))))

        await(service.retrieveSelfEmploymentsAdjustments(request) ) shouldBe Right(ResponseWrapper(correlationId, response))
      }
    }
  }
}
