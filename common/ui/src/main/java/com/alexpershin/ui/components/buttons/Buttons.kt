package com.alexpershin.ui.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexpershin.ui.components.Components
import com.alexpershin.ui.theme.MyStarlingAppTheme
import androidx.compose.material3.Button as MaterialButton

object Buttons {

    @Composable
    fun ContainedButton(
        text: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        padding: PaddingValues = DefaultButtonPadding
    ) {
        MaterialButton(
            modifier = Modifier
                .then(modifier)
                .padding(padding),
            onClick = onClick,
        ) {
            Text(text = text)
        }
    }


    private val DefaultButtonPadding = PaddingValues(horizontal = 16.dp)

}

@Preview
@Composable
internal fun ContainedButtonPreview() {
    MyStarlingAppTheme {
        Components.buttons.ContainedButton(
            onClick = {},
            text = "Contained button",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
