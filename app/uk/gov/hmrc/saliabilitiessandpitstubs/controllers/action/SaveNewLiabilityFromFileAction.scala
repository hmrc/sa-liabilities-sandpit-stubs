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

import org.apache.pekko.stream.Materializer
import play.api.libs.Files.TemporaryFile
import play.api.mvc.*
import play.api.mvc.Results.BadRequest
import uk.gov.hmrc.saliabilitiessandpitstubs.controllers.action.SaveNewLiabilityFromFileAction.MissingFileBadRequest
import uk.gov.hmrc.saliabilitiessandpitstubs.http.FileStreamliner
import uk.gov.hmrc.saliabilitiessandpitstubs.models.BalanceDetail
import uk.gov.hmrc.saliabilitiessandpitstubs.service.BalanceDetailService

import scala.concurrent.{ExecutionContext, Future}

private[controllers] trait SaveNewLiabilityFromFileAction(using service: BalanceDetailService)(using
  Materializer,
  ExecutionContext
):
  self: BaseController & FileStreamliner[BalanceDetail] =>

  val uploadBalances: String => Action[MultipartFormData[TemporaryFile]] = (nino: String) =>
    Action.async(parse.multipartFormData)(
      _.body
        .file("balances")
        .map { balanceFile =>
          extractBalanceDetailsSource(balanceFile.ref.path)
            .runForeach(service.addOrUpdateBalanceDetail(nino, _))
            .map(_ => Ok("Balances uploaded successfully"))
        }
        .getOrElse(MissingFileBadRequest)
    )

private object SaveNewLiabilityFromFileAction:
  private val MissingFileBadRequest = Future.successful(BadRequest("Missing file"))
