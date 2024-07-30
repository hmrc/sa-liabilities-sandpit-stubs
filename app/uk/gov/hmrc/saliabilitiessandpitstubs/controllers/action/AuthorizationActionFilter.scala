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
import play.api.mvc.*
import uk.gov.hmrc.http.Authorization
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendHeaderCarrierProvider
import uk.gov.hmrc.saliabilitiessandpitstubs.controllers.action.AuthorizationActionFilter.TokenBasedAction.*

import scala.concurrent.Future
import scala.concurrent.Future.*
import scala.util.matching.Regex
import scala.util.matching.Regex.Match

sealed trait AuthorizationActionFilter extends ActionFilter[Request]:
  def filter[A](request: Request[A]): Future[Option[Result]]

object AuthorizationActionFilter:
  abstract class OpenAuthAction extends AuthorizationActionFilter:
    override def filter[A](request: Request[A]): Future[Option[Result]] = successful(None)

  abstract class TokenBasedAction extends AuthorizationActionFilter:
    self: BackendHeaderCarrierProvider =>

    override def filter[A](implicit request: Request[A]): Future[Option[Result]] = successful {
      (for {
        token: Authorization <- hc.authorization
        tokenMatch: Match    <- tokenPattern findFirstMatchIn token
      } yield tokenMatch.extractTokenFromMatch) match {
        case Some(_) => None
        case None    => Some(unauthorisedResponse)
      }
    }

  private[action] object TokenBasedAction:
    private val tokenPattern: Regex          = "^Bearer (.+)$".r
    private val unauthorisedResponse: Result = Results.BadRequest(Json.obj("error" -> "Missing Authorization Header"))

    extension (regexMatch: Match) private inline def extractTokenFromMatch: String = regexMatch group 1

    extension (regex: Regex)
      private inline def findFirstMatchIn(auth: Authorization): Option[Match] = regex findFirstMatchIn auth.value
