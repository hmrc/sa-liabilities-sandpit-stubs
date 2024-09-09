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

import org.apache.pekko.stream.scaladsl.Source
import org.apache.pekko.stream.scaladsl.Source.single
import play.api.http.ContentTypes
import play.api.mvc.{Action, AnyContent, BaseController, Request}
import uk.gov.hmrc.saliabilitiessandpitstubs.controllers.action.WizardAction.{getProperty, html}
import uk.gov.hmrc.saliabilitiessandpitstubs.models.BalanceDetail
import uk.gov.hmrc.saliabilitiessandpitstubs.models.BalanceDetail.create
import uk.gov.hmrc.saliabilitiessandpitstubs.service.BalanceDetailService

private[controllers] trait WizardAction(using service: BalanceDetailService):
  self: BaseController =>

  def cometString() = Action(Ok.chunked(single(html)).as(ContentTypes.HTML))

  def submit(): Action[AnyContent] = Action { implicit request =>
    val nino             = request getProperty "nino"
    val payableAmount    = request getProperty "payableAmount"
    val payableDueDate   = request getProperty "payableDueDate"
    val pendingDueAmount = request getProperty "pendingDueAmount"
    val pendingDueDate   = request getProperty "pendingDueDate"
    val overdueAmount    = request getProperty "overdueAmount"
    val totalBalance     = request getProperty "totalBalance"

    val balance = create(payableAmount, payableDueDate, pendingDueAmount, pendingDueDate, overdueAmount, totalBalance)

    service.addOrUpdateBalanceDetail(nino, balance)

    Ok("Created balance for: " + nino)
  }

private object WizardAction:
  extension (request: Request[AnyContent])
    def getProperty(field: String): String = (request.body.asFormUrlEncoded get field).head

  private val html =
    """
      |<!DOCTYPE html>
      |<html>
      |<body>
      |  <form action="/stub/submit" method="post">
      |    <label for="nino">Nino</label>
      |    <input type="text" id="nino" name="nino" required><br><br>
      |
      |    <label for="payableAmount">PayableAmount</label>
      |    <input type="text" id="payableAmount" name="payableAmount"><br><br>
      |
      |    <label for="payableDueDate">PayableDueDate</label>
      |    <input type="text" id="payableDueDate" name="payableDueDate"><br><br>
      |
      |    <label for="pendingDueAmount">PendingDueAmount</label>
      |    <input type="text" id="pendingDueAmount" name="pendingDueAmount"><br><br>
      |
      |    <label for="pendingDueDate">PendingDueDate</label>
      |    <input type="text" id="pendingDueDate" name="pendingDueDate"><br><br>
      |
      |    <label for="overdueAmount">OverdueAmount</label>
      |    <input type="text" id="overdueAmount" name="overdueAmount"><br><br>
      |
      |    <label for="totalBalance">TotalBalance</label>
      |    <input type="text" id="totalBalance" name="totalBalance"><br><br>
      |
      |    <input type="submit" value="Submit">
      |  </form>
      |</body>
      |</html>
      |""".stripMargin
