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

import org.apache.pekko.stream.Materializer
import play.api.Logging
import play.api.mvc.ControllerComponents
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendBaseController
import uk.gov.hmrc.saliabilitiessandpitstubs.controllers.action.*
import uk.gov.hmrc.saliabilitiessandpitstubs.http.{FileStreamliner, Streamliner}
import uk.gov.hmrc.saliabilitiessandpitstubs.json.JsValidator
import uk.gov.hmrc.saliabilitiessandpitstubs.models.BalanceDetail
import uk.gov.hmrc.saliabilitiessandpitstubs.service.BalanceDetailService

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class BalanceCommandController @Inject() (
  val controllerComponents: ControllerComponents
)(using
  Materializer,
  ExecutionContext,
  BalanceDetailService,
  AuthorizationActionFilter,
  JsValidator[BalanceDetail]
) extends SaveNewLiability,
      SaveNewLiabilityFromFileAction,
      FileStreamliner[BalanceDetail],
      SaveGeneratedLiability,
      DeleteLiabilityAction,
      ReplaceExistingBalanceDetailAction,
      Streamliner[BalanceDetail],
      BackendBaseController,
      Logging
