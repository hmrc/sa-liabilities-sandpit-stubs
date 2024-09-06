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

import play.api.libs.json.Json
import play.api.mvc.Results.NotFound
import play.api.mvc.{Action, AnyContent, BaseController, Request, Result}
import uk.gov.hmrc.saliabilitiessandpitstubs.controllers.action.BalanceActions.BalanceLiabilityNotFoundResult
import uk.gov.hmrc.saliabilitiessandpitstubs.service.BalanceDetailService
import uk.gov.hmrc.saliabilitiessandpitstubs.utils.DelaySimulator

private[controllers] trait BalanceActions(using auth: AuthorizationActionFilter, service: BalanceDetailService):
  self: BaseController & DelaySimulator =>

  def getBalanceByNino(nino: String): Action[AnyContent] = (Action andThen auth).async { implicit request: Request[_] =>
    simulateNetworkConditions {
      service.balanceDetailsByNino(nino) match
        case Some(balance) => Ok(balance)
        case None          => BalanceLiabilityNotFoundResult
    }
  }

object BalanceActions:
  val BalanceLiabilityNotFoundResult: Result = NotFound(Json.obj("error" -> s"No balance found"))
