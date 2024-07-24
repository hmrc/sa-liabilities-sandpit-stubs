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
import uk.gov.hmrc.saliabilitiessandpitstubs.models.{OverdueAmount, *}
import uk.gov.hmrc.saliabilitiessandpitstubs.models.integration.BalanceDetail

import javax.inject.Inject
import scala.concurrent.Future

private[controllers] trait BalanceActions {
  self: BaseController =>

  var balanceDetailMap: Map[String, List[BalanceDetail]] = Map(
    "AA000000A" -> List(
      BalanceDetail(
        payableAmount = PayableAmount(100.00),
        payableDueDate = PayableDueDate("2024-07-20"),
        pendingDueAmount = PendingDueAmount(100.02),
        pendingDueDate = PendingDueDate("2024-08-20"),
        overdueAmount = OverdueAmount(100.03),
        totalBalance = TotalBalance(300.5)
      )
    ),
    "AA000000B" -> List(
      BalanceDetail(
        payableAmount = PayableAmount(200.00),
        payableDueDate = PayableDueDate("2024-07-20"),
        pendingDueAmount = PendingDueAmount(200.02),
        pendingDueDate = PendingDueDate("2024-08-20"),
        overdueAmount = OverdueAmount(200.03),
        totalBalance = TotalBalance(600.5)
      )
    ),
    "AA000000C" -> List(
      BalanceDetail(
        payableAmount = PayableAmount(300.00),
        payableDueDate = PayableDueDate("2024-07-20"),
        pendingDueAmount = PendingDueAmount(300.02),
        pendingDueDate = PendingDueDate("2024-08-20"),
        overdueAmount = OverdueAmount(300.03),
        totalBalance = TotalBalance(900.5)
      )
    )
  ).withDefaultValue(
    List(
      BalanceDetail(
        payableAmount = PayableAmount(0.00),
        payableDueDate = PayableDueDate("1999-01-01"),
        pendingDueAmount = PendingDueAmount(0.0),
        pendingDueDate = PendingDueDate("1999-01-01"),
        overdueAmount = OverdueAmount(0.0),
        totalBalance = TotalBalance(0.0)
      )
    )
  )

  def getBalanceByNino(nino: String): Action[AnyContent] = Action.async { implicit request: Request[_] =>
    Future.successful(Ok(Json.toJson(balanceDetailMap.apply(nino))))
  }
}

class BalanceController @Inject() (val controllerComponents: ControllerComponents)
  extends BalanceActions
    with BackendBaseController