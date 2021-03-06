package com.monstarlab.features.nstack

import dk.nodes.nstack.kotlin.NStack

/**
 * Generated by the NStack gradle plugin
 */
object RateReminderActions {

	private const val CRASH = "crash"
	private const val DONATED = "donated"

	suspend fun crash() {
		send(CRASH)
	}

	suspend fun donated() {
		send(DONATED)
	}

	private suspend fun send(action: String) {
		NStack.RateReminder.action(action)
	}
}
