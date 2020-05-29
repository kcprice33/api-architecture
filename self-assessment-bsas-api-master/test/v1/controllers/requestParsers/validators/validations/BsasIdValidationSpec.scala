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

package v1.controllers.requestParsers.validators.validations

import support.UnitSpec
import v1.models.errors.BsasIdFormatError
import v1.models.utils.JsonErrorValidators

class BsasIdValidationSpec extends UnitSpec with JsonErrorValidators {

  case class SetUp(bsasId: String)

  "validate" should {
    "return no errors" when {
      "a valid BSASId is supplied" in new SetUp("a54ba782-5ef4-47f4-ab72-495406665ca9"){

        BsasIdValidation.validate(bsasId).isEmpty shouldBe true
      }
    }

    "return an error" when {
      "a invalid BSASId is supplied" in new SetUp("a54ba782-5ef4-47f4-ab72-zxasxawdsaw"){

        val validationResult = BsasIdValidation.validate(bsasId)

        validationResult.length shouldBe 1
        validationResult.head shouldBe BsasIdFormatError
      }
    }
  }
}