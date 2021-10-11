package com.mathewsachin.fategrandautomata.scripts

import com.mathewsachin.fategrandautomata.scripts.models.CommandCard
import com.mathewsachin.fategrandautomata.scripts.models.FieldSlot
import com.mathewsachin.fategrandautomata.scripts.modules.ServantTracker
import kotlin.time.Duration

sealed class ScriptNotify {
    object CEGet: ScriptNotify()
    object CEDropped: ScriptNotify()
    class WaitForAPRegen(val minutes: Int = 1): ScriptNotify()
    class FailedToDetermineCards(val cards: List<CommandCard.Face>): ScriptNotify()
    class SupportListUpdatingIn(val time: Duration): ScriptNotify()
    class BetweenRuns(val refills: Int, val runs: Int): ScriptNotify()
}

sealed class ScriptLog {
    object DefaultSupportBounds: ScriptLog()
    object DefaultMasterOffset: ScriptLog()
    object RearrangingCards: ScriptLog()
    class CurrentParty(val party: Int?): ScriptLog()
    class MaxSkills(
        val needMaxedSkills: List<Boolean>,
        val isSkillMaxed: List<Boolean>
    ): ScriptLog()
    class ClickingNPs(val nps: Iterable<CommandCard.NP>): ScriptLog()
    class ClickingCards(val cards: Iterable<CommandCard.Face>): ScriptLog()
    class NPsGroupedByFaceCards(val groups: Map<CommandCard.NP, List<CommandCard.Face>>): ScriptLog()
    class SupportFaceCardGroup(val group: List<CommandCard.Face>): ScriptLog()
    class FaceCardGroups(val groups: List<List<CommandCard.Face>>): ScriptLog()
    class ServantEnteredSlot(
        val servant: ServantTracker.TeamSlot,
        val slot: FieldSlot
    ): ScriptLog()
    class CardsBelongToServant(
        val cards: List<CommandCard.Face>,
        val servant: ServantTracker.TeamSlot,
        val isSupport: Boolean = false
    ): ScriptLog()
    class CardsNotPickedByServantPriority(
        val cards: List<CommandCard.Face>
    ): ScriptLog()
}

interface IScriptMessages {
    fun notify(action: ScriptNotify)

    fun log(item: ScriptLog)
}