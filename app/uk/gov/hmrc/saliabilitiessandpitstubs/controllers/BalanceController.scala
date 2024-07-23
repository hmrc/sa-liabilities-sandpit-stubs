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

package uk.gov.hmrc.saliabilitiessandpitstubs.controllers

import play.api.libs.json.Json
import play.api.mvc.*
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendBaseController
import uk.gov.hmrc.saliabilitiessandpitstubs.models.*
import uk.gov.hmrc.saliabilitiessandpitstubs.models.integration.BalanceDetail

import javax.inject.Inject
import scala.concurrent.Future

private[controllers] trait BalanceActions {
  self: BaseController =>

  def getBalanceByNino(nino: String): Action[AnyContent] = Action.async { implicit request: Request[_] =>
    nino match{
      case "AA000000A" =>
        val balanceDetails = List(
          BalanceDetail(
            payableAmount = PayableAmount(100.00),
            payableDueDate = PayableDueDate("2024-07-20"),
            pendingDueAmount = PendingDueAmount(200.00),
            pendingDueDate = PendingDueDate("2024-08-20"),
            overdueAmount = OverdueAmount(50.00),
            totalBalance = TotalBalance(350.1)
          )
        )
        Future.successful(Ok(Json.toJson(balanceDetails)))
      case "AA000000B" =>
        val balanceDetails = List(
          BalanceDetail(
            payableAmount = PayableAmount(200.00),
            payableDueDate = PayableDueDate("2024-07-20"),
            pendingDueAmount = PendingDueAmount(400.00),
            pendingDueDate = PendingDueDate("2024-08-20"),
            overdueAmount = OverdueAmount(100.00),
            totalBalance = TotalBalance(700.2)
          )
        )
        Future.successful(Ok(Json.toJson(balanceDetails)))
    }
  }
}

class BalanceController @Inject() (val controllerComponents: ControllerComponents)
  extends BalanceActions
    with BackendBaseController