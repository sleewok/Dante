package at.shockbytes.dante.ui.activity

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import at.shockbytes.dante.dagger.AppComponent
import at.shockbytes.dante.flagging.FeatureFlag
import at.shockbytes.dante.flagging.FeatureFlagging
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import at.shockbytes.dante.ui.activity.core.ContainerBackNavigableActivity
import at.shockbytes.dante.ui.fragment.BackupFragment
import at.shockbytes.dante.ui.fragment.LegacyBackupFragment
import javax.inject.Inject
import at.shockbytes.dante.ui.viewmodel.BackupViewModel
import pub.devrel.easypermissions.EasyPermissions

class BackupActivity : ContainerBackNavigableActivity(), EasyPermissions.PermissionCallbacks {

    @Inject
    lateinit var featureFlagging: FeatureFlagging

    override val displayFragment: Fragment by lazy {
        if (featureFlagging[FeatureFlag.RefactoredBackups]) {
            BackupFragment.newInstance()
        } else {
            LegacyBackupFragment.newInstance()
        }
    }

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private lateinit var viewModel: BackupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, vmFactory)[BackupViewModel::class.java]
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun injectToGraph(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) = Unit

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        // Reload data sources once external permission is granted
        viewModel.connect(this, forceReload = true)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, BackupActivity::class.java)
        }
    }
}
