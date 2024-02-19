import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.example.hospedajetema3.R
import com.example.hospedajetema3.fragments.fragment_notes.InsigniasFragment
import com.example.hospedajetema3.fragments.fragment_notes.NotesFragment
import com.example.hospedajetema3.fragments.fragment_notes.OtherFragment

class FragmentNotas : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notas, container, false)

        val viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)

        // Configurar ViewPager con su adaptador personalizado
        viewPager.adapter = MyPagerAdapter(childFragmentManager)

        // Vincular el TabLayout con el ViewPager
        tabLayout.setupWithViewPager(viewPager)

        return view
    }

    private class MyPagerAdapter(fm: androidx.fragment.app.FragmentManager) :
        androidx.fragment.app.FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int {
            return 4 // Número total de fragmentos
        }

        override fun getItem(position: Int): androidx.fragment.app.Fragment {
            // Retorna el Fragment correspondiente a cada posición
            return when (position) {
                0 -> NotesFragment()
                1 -> OtherFragment()
                2 -> RewardFragment()
                3 -> InsigniasFragment()
                else -> NotesFragment()
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "Notas"
                1 -> "Otro"
                2 -> "Coleccion"
                3 -> "Insignias"
                else -> "Sección ${position + 1}"
            }
        }
    }
}
