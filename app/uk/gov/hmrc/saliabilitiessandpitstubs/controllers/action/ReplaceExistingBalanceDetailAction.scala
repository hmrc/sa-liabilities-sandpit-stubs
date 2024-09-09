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

import play.api.libs.json.JsValue
import play.api.libs.json.Json.obj
import play.api.mvc.{Action, AnyContent, BaseController, Request, Result}
import play.api.mvc.Results.{BadRequest, Created}
import uk.gov.hmrc.saliabilitiessandpitstubs.controllers.action.ReplaceExistingBalanceDetailAction.{CreatedNewLiabilityResult, InvalidBalanceDetailErrorResult}
import uk.gov.hmrc.saliabilitiessandpitstubs.json.JsValidator
import uk.gov.hmrc.saliabilitiessandpitstubs.models.BalanceDetail
import uk.gov.hmrc.saliabilitiessandpitstubs.service.BalanceDetailService

import scala.concurrent.Future.successful
import scala.concurrent.{ExecutionContext, Future}

private[controllers] trait ReplaceExistingBalanceDetailAction(using
  auth: AuthorizationActionFilter,
  service: BalanceDetailService,
  jsValidator: JsValidator[BalanceDetail]
):
  self: BaseController =>

  val replaceExistingBalanceDetailAction: String => Action[JsValue] = nino =>
    (Action andThen auth async parse.json)(
      (_: Request[JsValue]).body
        .validate(jsValidator.validate)
        .fold(
          errors => handleValidationError,
          balanceDetail => {
            service.replaceBalanceDetail(nino, balanceDetail)
            handleSuccess
          }
        )
    )

  private def handleValidationError: Future[Result] = successful(InvalidBalanceDetailErrorResult)

  private def handleSuccess: Future[Result] = successful(CreatedNewLiabilityResult)

private[this] object ReplaceExistingBalanceDetailAction:
  val InvalidBalanceDetailErrorResult: Result = BadRequest(obj("error" -> "Invalid balance detail format"))
  val CreatedNewLiabilityResult: Result       = Created(obj("status" -> "Balance updated successfully"))
