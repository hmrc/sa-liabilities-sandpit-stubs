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

import org.apache.pekko
import org.apache.pekko.*
import org.apache.pekko.actor.*
import org.apache.pekko.pattern.after

import java.util.concurrent.TimeUnit
import scala.concurrent.Future.*
import scala.concurrent.duration.*
import scala.concurrent.{ExecutionContext, Future, TimeoutException}
import scala.util.Random

trait DelayStrategy(using random: Random, executionContext: ExecutionContext) {

  def simulateRandomDelay[T](result: T, maxDelay: FiniteDuration = 1000.millis): Future[T] =
    val randomDelay = FiniteDuration(random.between(50, maxDelay.toMillis), TimeUnit.MILLISECONDS)
    after(randomDelay, using = ActorSystem().scheduler)(successful(result))

  def simulateFailureWithDelay[T](result: T, delay: FiniteDuration = 1.seconds, failureRate: Double = 0.5): Future[T] =
    if random.nextDouble < failureRate then
      after(delay, using = ActorSystem().scheduler)(failed(new RuntimeException("Simulated failure")))
    else after(delay, using = ActorSystem().scheduler)(successful(result))

  def simulateThrottling[T](result: T, throttleDuration: FiniteDuration = 3.seconds): Future[T] =
    after(throttleDuration, using = ActorSystem().scheduler)(successful(result))

  def simulateTimeout[T](timeoutDuration: FiniteDuration = 10.seconds): Future[T] =
    after(timeoutDuration, using = ActorSystem().scheduler)(failed(new TimeoutException("Simulated timeout")))

}
