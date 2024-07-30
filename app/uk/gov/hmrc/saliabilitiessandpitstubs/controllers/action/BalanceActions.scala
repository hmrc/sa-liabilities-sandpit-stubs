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

import play.api.Logging
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, Request}
import uk.gov.hmrc.saliabilitiessandpitstubs.data.BalanceDetailDataFactory.balanceDetailMap

import scala.concurrent.Future
import scala.concurrent.Future.successful

private[controllers] trait BalanceActions(using auth: AuthorizationActionFilter) {
  self: BaseController & Logging =>

  def getBalanceByNino(nino: String): Action[AnyContent] = (Action andThen auth).async { implicit request: Request[_] =>
    logger.debug(s"Looking up balance for NINO: $nino")
    balanceDetailMap.get(nino) match {
      case Some(balance) =>
        logger.debug(s"Found balance for NINO $nino: $balance")
        successful(Ok(balance))
      case None          =>
        logger.debug(s"No balance found for NINO $nino")
        val notFoundResult = NotFound(Json.obj("error" -> s"No balance found for NINO $nino"))
        successful(notFoundResult)
    }
  }
}
