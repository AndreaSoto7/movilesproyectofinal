import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapi.databinding.ItemReservationBinding
import com.example.loginapi.models.dto.Reservation

class ReservationAdapter(private val onReservationClick: (Reservation) -> Unit) :
    ListAdapter<Reservation, ReservationAdapter.ReservationViewHolder>(ReservationDiffCallback()) {

    class ReservationViewHolder(private val binding: ItemReservationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reservation: Reservation, onReservationClick: (Reservation) -> Unit) {
            binding.tvRestaurantName.text =
                "Restaurante: ${reservation.restaurant_id}" // Reemplaza con el nombre del restaurantebinding.tvDate.text = "Fecha: ${reservation.date}"
            binding.tvTime.text = "Hora: ${reservation.time}"
            binding.tvPeople.text = "Personas: ${reservation.people}"

            binding.root.setOnClickListener {
                onReservationClick(reservation)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val binding =
            ItemReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = getItem(position)
        holder.bind(reservation, onReservationClick)
    }

    class ReservationDiffCallback : DiffUtil.ItemCallback<Reservation>() {
        override fun areItemsTheSame(oldItem: Reservation, newItem: Reservation): Boolean {
            return oldItem.restaurant_id == newItem.restaurant_id // Reemplaza con el ID único de la reserva
        }

        override fun areContentsTheSame(oldItem: Reservation, newItem: Reservation): Boolean {
            return oldItem == newItem
        }
    }
}