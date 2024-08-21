package uk.gov.hmrc.saliabilitiessandpitstubs

import com.google.inject.Inject
import uk.gov.hmrc.saliabilitiessandpitstubs.time.LocalDateExtensions

import scala.util.Random

package object generator:

  case class DefaultBalanceDetailGenerator @Inject() (random: Random)
      extends BalanceDetailGenerator(using LocalDateExtensions, random)
