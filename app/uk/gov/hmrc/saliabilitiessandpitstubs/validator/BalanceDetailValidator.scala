/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.saliabilitiessandpitstubs.validator

import play.api.libs.json.*
import uk.gov.hmrc.saliabilitiessandpitstubs.json.JsValidator
import uk.gov.hmrc.saliabilitiessandpitstubs.models.BalanceDetail

class BalanceDetailValidator(val requiredFields: Set[String]) extends JsValidator[BalanceDetail]:

  override def validate(json: JsValue): JsResult[BalanceDetail] =
    json.validate[JsObject].flatMap { obj =>
      if requiredFields.subsetOf(obj.keys) then obj.validate[BalanceDetail]
      else JsError(s"Missing required fields: ${requiredFields.diff(obj.keys).mkString(", ")}")
    }
