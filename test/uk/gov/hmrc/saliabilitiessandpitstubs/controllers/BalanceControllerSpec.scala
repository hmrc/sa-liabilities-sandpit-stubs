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

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.http.Status
import play.api.mvc.ControllerComponents
import play.api.test.Helpers.*
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.saliabilitiessandpitstubs.controllers.action.DefaultOpenAuthAction

class BalanceControllerSpec extends AnyWordSpec with Matchers {

  private val fakeRequest                      = FakeRequest("GET", "/AA000000A")
  private val components: ControllerComponents = Helpers.stubControllerComponents()
  private val controller                       = new BalanceController(components, new DefaultOpenAuthAction(components.executionContext))

  "GET /balance/AA000000A" should {
    "return 200" in {
      val result = controller.getBalanceByNino("AA000000A")(fakeRequest)
      status(result) shouldBe Status.OK
    }
  }
}
