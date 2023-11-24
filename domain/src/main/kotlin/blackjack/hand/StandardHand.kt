package blackjack.hand

import blackjack.card.Card
import blackjack.card.CardRank

internal class StandardHand(
    override val cards: Set<Card> = emptySet()
) : Hand {
    override fun addCard(card: Card): StandardHand = StandardHand(cards = cards + card)

    override fun calculateBestValue(): Int {
        val sumWithoutAces = cards.filter { it.rank != CardRank.ACE }.sumOf { cardValue(it) }
        val aceCount = cards.count { it.rank == CardRank.ACE }
        return calculateBestAceValue(sumWithoutAces, aceCount)
    }

    override fun calculateMinValue(): Int {
        val sumWithoutAces = cards.filter { it.rank != CardRank.ACE }.sumOf { cardValue(it) }
        val aceCount = cards.count { it.rank == CardRank.ACE }
        return calculateMinAceValue(sumWithoutAces, aceCount)
    }

    private fun cardValue(card: Card): Int = when (card.rank) {
        CardRank.KING, CardRank.QUEEN, CardRank.JACK -> FACE_CARD_VALUE
        else -> card.rank.ordinal + 1
    }

    private fun calculateBestAceValue(sumWithoutAces: Int, aceCount: Int): Int {
        var sum = sumWithoutAces
        repeat(aceCount) {
            sum += if (sum + ACE_HIGH_VALUE > MAX_HAND_VALUE) ACE_LOW_VALUE else ACE_HIGH_VALUE
        }
        return sum
    }

    private fun calculateMinAceValue(sumWithoutAces: Int, aceCount: Int): Int {
        var sum = sumWithoutAces
        repeat(aceCount) {
            sum += ACE_LOW_VALUE
        }
        return sum
    }

    companion object {
        private const val FACE_CARD_VALUE = 10
        private const val MAX_HAND_VALUE = 21
        private const val ACE_HIGH_VALUE = 11
        private const val ACE_LOW_VALUE = 1
    }
}