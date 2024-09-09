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
    """<!DOCTYPE html>
      |<html>
      |<head>
      |  <title>Submit Balance Details</title>
      |  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
      |</head>
      |<body>
      |  <div class="container mt-5">
      |    <h2 class="text-center">Submit Balance Details</h2>
      |    <form action="/stub/submit" method="post">
      |      <div class="form-group">
      |        <label for="nino">National Insurance Number (NINO):</label>
      |        <input type="text" id="nino" name="nino" class="form-control" required placeholder="Enter NINO">
      |      </div>
      |
      |      <div class="row">
      |        <div class="col-md-6">
      |          <div class="form-group">
      |            <label for="payableAmount">Payable Amount:</label>
      |            <input type="text" id="payableAmount" name="payableAmount" class="form-control" placeholder="Enter the total amount payable">
      |          </div>
      |        </div>
      |        <div class="col-md-6">
      |          <div class="form-group">
      |            <label for="payableDueDate">Payable Due Date:</label>
      |            <input type="date" id="payableDueDate" name="payableDueDate" class="form-control" placeholder="Select the due date for the payable amount">
      |          </div>
      |        </div>
      |      </div>
      |
      |      <div class="row">
      |        <div class="col-md-6">
      |          <div class="form-group">
      |            <label for="pendingDueAmount">Pending Due Amount:</label>
      |            <input type="text" id="pendingDueAmount" name="pendingDueAmount" class="form-control" placeholder="Enter the amount currently pending">
      |          </div>
      |        </div>
      |        <div class="col-md-6">
      |          <div class="form-group">
      |            <label for="pendingDueDate">Pending Due Date:</label>
      |            <input type="date" id="pendingDueDate" name="pendingDueDate" class="form-control" placeholder="Select the due date for the pending amount">
      |          </div>
      |        </div>
      |      </div>
      |
      |      <div class="form-group">
      |        <label for="overdueAmount">Overdue Amount:</label>
      |        <input type="text" id="overdueAmount" name="overdueAmount" class="form-control" placeholder="Enter the amount currently overdue">
      |      </div>
      |
      |      <div class="form-group">
      |        <label for="totalBalance">Total Balance:</label>
      |        <input type="text" id="totalBalance" name="totalBalance" class="form-control" placeholder="Enter the total balance">
      |      </div>
      |
      |      <button type="submit" class="btn btn-primary">Submit</button>
      |    </form>
      |  </div>
      |</body>
      |</html>""".stripMargin
