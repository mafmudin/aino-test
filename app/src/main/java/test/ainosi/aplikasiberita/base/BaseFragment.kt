package test.ainosi.aplikasiberita.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<VB: ViewBinding, VM: ViewModel>(
    private val layout: Int,
    private val viewModelClass: Class<VM>?,
    private val viewModelTest: VM?
) : Fragment(), LifecycleObserver {

    lateinit var viewModel: VM
    protected val LOCATION_PERMISSIONS = 101

    abstract fun getViewBinding(): VB
    private var _binding: ViewBinding? = null
    protected val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        viewModelClass?.let {
            viewModel = viewModelTest ?: ViewModelProvider(requireActivity()).get(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateBinding()
        setListener()
    }

    protected open fun setListener(){}
    protected open fun inflateBinding(){}

    protected fun isLocationPermissionGranted(): Boolean {
        return (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }

    protected fun listLocationPermission(): Array<String> {
        return arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    protected fun <T : Any> gotoIntent(classIntent : Class<T>, bundle : Bundle? = null, isFinish : Boolean = false){
        val intent = Intent(this.activity, classIntent)
        if(bundle != null)
            intent.putExtras(bundle)
        startActivity(intent)
        if(isFinish)
            activity?.finish()
    }


}