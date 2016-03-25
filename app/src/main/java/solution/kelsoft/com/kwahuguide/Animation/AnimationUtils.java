package solution.kelsoft.com.kwahuguide.Animation;

import android.support.v7.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by Rukuvwe on 3/22/2016.
 */
public class AnimationUtils {

    public static void animate(RecyclerView.ViewHolder holder, boolean goesDown) {
        // ObjectAnimator animator = ObjectAnimator.ofFloat(holder.itemView, "translationY",goesDown==true?200: -200, 0);
        //    animator.setDuration(1000);
        //    animator.start();
        YoYo.with(Techniques.BounceIn)
                .duration(1000)
                .playOn(holder.itemView);
    }
}
