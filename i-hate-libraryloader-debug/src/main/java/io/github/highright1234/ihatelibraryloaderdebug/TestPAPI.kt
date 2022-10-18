package io.github.highright1234.ihatelibraryloaderdebug

import io.github.highright1234.ihatelibraryloader.papi.papi

object TestPAPI {
    fun register() = papi {
        expansion("debug") {
            then("asdf") {
                executes {
                    "faq"
                }
            }
            argument("value") {
                executes {
                    val value: String by it.arguments
                    value
                }
            }
        }
    }
}