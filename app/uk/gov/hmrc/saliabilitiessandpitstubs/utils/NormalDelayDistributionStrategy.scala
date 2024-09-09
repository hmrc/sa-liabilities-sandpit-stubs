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

package uk.gov.hmrc.saliabilitiessandpitstubs.utils

import scala.concurrent.Future
import scala.concurrent.Future.successful
import scala.util.Random

trait NormalDelayDistributionStrategy(using random: Random) extends DelaySimulator:
  self: DelayStrategy =>

  override def simulateNetworkConditions[T](result: T): Future[T] = random.nextGaussian() match
    case p if p < -1.0 => simulateFailureWithDelay(result)
    case p if p < -0.5 => simulateRandomDelay(result)
    case p if p < 0.0  => simulateThrottling(result)
    case p if p < 1.0  => simulateTimeout()
    case _             => successful(result)
