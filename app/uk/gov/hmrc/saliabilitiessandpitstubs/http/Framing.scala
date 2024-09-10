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

package uk.gov.hmrc.saliabilitiessandpitstubs.http

import org.apache.pekko.NotUsed
import org.apache.pekko.stream.scaladsl.Flow
import org.apache.pekko.stream.scaladsl.Framing.delimiter
import org.apache.pekko.util.ByteString
import uk.gov.hmrc.saliabilitiessandpitstubs.http.Framing.{MaximumFrameLength, NewLineDelimiter}

trait Framing:

  val networkFramingDelimiter: Flow[ByteString, ByteString, NotUsed] =
    delimiter(delimiter = NewLineDelimiter, maximumFrameLength = MaximumFrameLength, allowTruncation = true)

private object Framing:
  private val NewLineDelimiter: ByteString = ByteString("\n")
  private val MaximumFrameLength: Int      = 1024
