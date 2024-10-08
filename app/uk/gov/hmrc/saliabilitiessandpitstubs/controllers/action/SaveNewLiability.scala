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

package uk.gov.hmrc.saliabilitiessandpitstubs.controllers.action

import play.api.libs.json.Json.obj
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.Results.{BadRequest, Created}
import play.api.mvc.{Action, BaseController, Request, Result}
import uk.gov.hmrc.saliabilitiessandpitstubs.controllers.action.SaveNewLiability.{CreatedNewLiabilityResult, InvalidBalanceDetailErrorResult}
import uk.gov.hmrc.saliabilitiessandpitstubs.json.JsValidator
import uk.gov.hmrc.saliabilitiessandpitstubs.models.BalanceDetail
import uk.gov.hmrc.saliabilitiessandpitstubs.service.BalanceDetailService

import scala.concurrent.Future
import scala.concurrent.Future.*

private[controllers] trait SaveNewLiability(using
  auth: AuthorizationActionFilter,
  service: BalanceDetailService,
  jsValidator: JsValidator[BalanceDetail]
):
  self: BaseController =>

  val saveNewBalanceByNino: String => Action[JsValue] = nino =>
    (Action andThen auth async parse.json)(
      (_: Request[JsValue]).body
        .validate(jsValidator.validate)
        .fold(
          errors => handleValidationError,
          balanceDetail => {
            service.addOrUpdateBalanceDetail(nino, balanceDetail)
            handleSuccess
          }
        )
    )

  private def handleValidationError: Future[Result] = successful(InvalidBalanceDetailErrorResult)

  private def handleSuccess: Future[Result] = successful(CreatedNewLiabilityResult)

private[this] object SaveNewLiability:
  val InvalidBalanceDetailErrorResult: Result = BadRequest(obj("error" -> "Invalid balance detail format"))
  val CreatedNewLiabilityResult: Result       = Created(obj("status" -> "Balance updated successfully"))
