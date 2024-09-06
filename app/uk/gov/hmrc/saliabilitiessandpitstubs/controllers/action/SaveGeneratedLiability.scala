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
import uk.gov.hmrc.saliabilitiessandpitstubs.controllers.action.SaveGeneratedLiability.CreatedNewLiabilityResult
import uk.gov.hmrc.saliabilitiessandpitstubs.models.BalanceDetail
import uk.gov.hmrc.saliabilitiessandpitstubs.service.BalanceDetailService

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.Future.successful

private[controllers] trait SaveGeneratedLiability(using
  auth: AuthorizationActionFilter,
  service: BalanceDetailService,
  executionContext: ExecutionContext
):
  self: BaseController =>

  val saveGeneratedBalanceByNino: String => Action[AnyContent] = (nino: String) =>
    Action andThen auth async { implicit request: Request[AnyContent] =>
      Future(service addOrUpdateGeneratedBalanceDetail nino) map (_ => CreatedNewLiabilityResult)
    }

private[this] object SaveGeneratedLiability:
  val CreatedNewLiabilityResult: Result = Created(obj("status" -> "Balance updated successfully"))
