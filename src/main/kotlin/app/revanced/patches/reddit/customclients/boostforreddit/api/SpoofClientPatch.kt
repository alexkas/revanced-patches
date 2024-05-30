package app.revanced.patches.reddit.customclients.boostforreddit.api

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.fingerprint.MethodFingerprintResult
import app.revanced.patches.reddit.customclients.BaseSpoofClientPatch
import app.revanced.patches.reddit.customclients.boostforreddit.api.fingerprints.GetClientIdFingerprint
import app.revanced.patches.reddit.customclients.boostforreddit.api.fingerprints.LoginActivityOnCreateFingerprint

@Suppress("unused")
object SpoofClientPatch : BaseSpoofClientPatch(
    redirectUri = "http://rubenmayayo.com",
    clientIdFingerprints = setOf(GetClientIdFingerprint),
    userAgentFingerprints = setOf(LoginActivityOnCreateFingerprint),
    compatiblePackages = setOf(CompatiblePackage("com.rubenmayayo.reddit"))
) {
    override fun Set<MethodFingerprintResult>.patchClientId(context: BytecodeContext) {
        first().mutableMethod.addInstructions(
            0,
            """
                const-string v0, "$clientId"
                return-object v0
            """
        )
    }

    override fun Set<MethodFingerprintResult>.patchUserAgent(context: BytecodeContext) {
        first().mutableMethod.addInstructions(
            0,  // Adjust this index according to the correct line in the targeted method
            """
                const-string v0, "com.rubenmayayo.reddit:1.12.12-210011212"
                invoke-virtual {p0, v0}, Landroid/webkit/WebSettings;->setUserAgentString(Ljava/lang/String;)V
                return-object v0
            """
        )
    }
}
