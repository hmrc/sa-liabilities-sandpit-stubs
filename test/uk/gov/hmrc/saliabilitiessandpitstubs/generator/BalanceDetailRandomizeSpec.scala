package uk.gov.hmrc.saliabilitiessandpitstubs.generator

import com.github.javafaker.Faker
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.{doReturn, spy, times, verify, when}
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.auth.core.syntax.retrieved.authSyntaxForRetrieved
import uk.gov.hmrc.saliabilitiessandpitstubs.generator.BalanceDetailRandomizeSpec.{date, spiedRandom}
import uk.gov.hmrc.saliabilitiessandpitstubs.generator.{BalanceDetailGenerator, DefaultBalanceDetailGenerator}
import uk.gov.hmrc.saliabilitiessandpitstubs.models.{BalanceDetail, OverdueAmount, PayableAmount, PayableDueDate, PendingDueAmount, PendingDueDate, TotalBalance}
import uk.gov.hmrc.saliabilitiessandpitstubs.service.BalanceDetailService
import uk.gov.hmrc.saliabilitiessandpitstubs.time.{DefaultFutureDateGenerator, FakerExtensions, FutureDateGenerator, LiveSystemLocalDate}

import java.time.LocalDate
import scala.util.Random

object BalanceDetailRandomizeSpec:

  given spiedRandom: Random = spy(new Random(42))
  
  doReturn(1).when(spiedRandom).nextInt(anyInt())

  given date: FutureDateGenerator = DefaultFutureDateGenerator(using LiveSystemLocalDate, spiedRandom)

class BalanceDetailGeneratorSpec extends AnyFunSuiteLike,
  BalanceDetailRandomize(using date, spiedRandom),
  Matchers {

  test("generate should create a BalanceDetail with correct field types") {
    val balanceDetail: BalanceDetail = generate

    balanceDetail.payableAmount.get shouldBe a [PayableAmount]
    balanceDetail.payableDueDate.get shouldBe a [PayableDueDate]
    balanceDetail.pendingDueAmount.get shouldBe a [PendingDueAmount]
    balanceDetail.overdueAmount.get shouldBe a [OverdueAmount]
    balanceDetail.totalBalance.get shouldBe a [TotalBalance]
  }

  test("each field of the BalanceDetail object needs to be randomised") {
    val balanceDetail: BalanceDetail = generate
    val expectedDate: LocalDate = LocalDate.now().plusDays(1)
    val expectedAmount: Int = 1
    val expectedTotal: Int = 3

    balanceDetail.payableAmount.get shouldEqual expectedAmount
    balanceDetail.payableDueDate.get shouldEqual expectedDate.toString
    balanceDetail.pendingDueAmount.get shouldEqual expectedAmount
    balanceDetail.pendingDueDate.get shouldEqual expectedDate.toString
    balanceDetail.overdueAmount.get shouldEqual expectedAmount

    //verify(spiedRandom, times(5)).nextInt(org.mockito.ArgumentMatchers.anyInt())

    balanceDetail.totalBalance.get shouldEqual expectedTotal
  }
}
