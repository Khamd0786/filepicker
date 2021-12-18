package com.hammad.file_picker.filePicker

import androidx.activity.ComponentActivity
import androidx.core.app.ActivityOptionsCompat

/**
 * [FilePicker] is a class, which provide a feature to select or pick file from the FileManager.
 * User can easily use it with just some simple implementations.
 *
 *
 * #### For more information
 * @see FilePicker.with
 * @see FilePicker.Builder
 * @author Hammad Khan
 */
class FilePicker private constructor(
    private val componentActivity: ComponentActivity,
    private val fileConfig: Config
) {
    companion object {

        /**
         * This method provide a [launch][FilePickerResultLauncher.launch], which launches FileManager.
         * ### Example of use: #Kotlin
         * ```
         *     class MainActivity: AppCompatActivity() {
         *          FilePicker.with(this) { list: List<UriData> ->
         *              //do something with this list
         *          }.launch("<any_mime_type>", "<additional_mime_type_optional>", ...)
         *     }
         * ```
         *
         * ### Example of use: #Java
         * ```
         *      class FragmentActivity extends AppCompatActivity(){
         *          FilePicker.with(this, list -> {
         *              //do something with the list
         *          }).launch("<any_mime_type>", "<additional_mime_type_optional>", ...)
         *      }
         * ```
         *
         * @param componentActivity is a parent class of FragmentActivity and AppCompatActivity that is why
         * both contains instance of [ComponentActivity].
         * @param onPickedCallback is a listener which provide list of [UriData]
         * @return Instance of [FilePickerResultLauncher] which provide a method such as [launch][FilePickerResultLauncher.launch]
         * @see FilePickerResultLauncher.launch
         * @author Hammad Khan
         */
        @JvmStatic
        fun with(
            componentActivity: ComponentActivity,
            onPickedCallback: OnFilePickedListener
        ): FilePickerResultLauncher {
            return object : FilePickerResultLauncher(componentActivity, Config()) {
                override fun onFilesPicked(list: List<UriData>) {
                    onPickedCallback.onFilePicked(list)
                }
            }
        }
    }

    /**
     *
     *
     * ### Example of use: #Kotlin
     * ```
     *     class MainActivity: AppCompatActivity() {
     *          val filePicker = FilePicker.Builder(this)
     *                  .setMimeType("<any_mime_type>", "<additional_mime_type_optional>", ...)
     *                  .build()
     *
     *          filePicker.pick { list: List<UriData> ->
     *                              //do something with this list
     *                          }
     *     }
     * ```
     *
     * ### Example of use: #Java
     * ```
     *      class FragmentActivity extends AppCompatActivity(){
     *          FilePicker picker = new FilePicker.Builder(this).setMimeType("<any_mime_type>", "<additional_mime_type_optional>", ...)
     *          .build();
     *          picker.pick(list -> {
     *              //do something with the list
     *          });
     *      }
     * ```
     * @author Hammad Khan
     */
    fun pick(onFilesPicked: OnFilePickedListener) {
        launch(onFilesPicked)
    }

    private fun launch(onFilesPickedOn: OnFilePickedListener) {
        object : FilePickerResultLauncher(componentActivity, fileConfig) {
            override fun onFilesPicked(list: List<UriData>) {
                onFilesPickedOn.onFilePicked(list)
            }
        }.launch(fileConfig.type)
    }


    /**
     * It provide the flexibility of customising FilePicker according to the Preferences.
     * ### Example of use: #Kotlin
     * ```
     *      class MyActivity: FragmentActivity() {
     *
     *          val filePicker = FilePicker.Builder(this)
     *                              .setMimeType("your_mime_type", "<additional_mime_type>", ....)
     *                              .build()
     *          filePicker.ping {list -> // do something with the list}
     *      }
     * ```
     *
     * ### Example of use: #Java
     * ```
     *      class MyActivity extends FragmentActivity() {
     *
     *          FilePicker filePicker = new FilePicker.Builder(this)
     *                              .setMimeType("your_mime_type", "<additional_mime_type>", ....)
     *                              .build();
     *          filePicker.ping (list -> {
     *              // do something with the list
     *          });
     *      }
     * ```
     */
    class Builder(private val componentActivity: ComponentActivity) {
        private var fileConfig: Config = Config()

        /**
         * @param mimeTypes is accept many parameters of type [MimeType],
         * ### Example:
         * ```
         *      setMimeTypes(MimeType.IMAGE)
         * ```
         * #### OR
         * ```
         *      setMimeTypes(MimeType.IMAGE, MimeType.DOC_PDF, ....etc)
         * ```
         */
        fun setMimeTypes(vararg mimeTypes: MimeType): Builder {
            fileConfig.type = mimeTypes.map { it.value }
            return this
        }

        /**
         * @param mimeTypes is accept many parameters of type [String],
         * ### Example:
         * ```
         *      setMimeTypes("image/jpeg")
         * ```
         * #### OR
         * ```
         *      setMimeTypes("image/jpeg", "application/pdf", ....etc)
         * ```
         */
        fun setMimeTypes(vararg mimeTypes: String): Builder {
            fileConfig.type = mimeTypes.asList()
            return this
        }

        /**
         * @param mimeTypes is accept many parameters of type [String],
         * ### Example:
         * ```
         *      val list = listOf("image/png", "application/pdf", ... etc)
         *      setMimeTypes(list)
         * ```
         */
        fun setMimeTypes(mimeTypes: List<String>): Builder {
            fileConfig.type = mimeTypes
            return this
        }

        /**
         * It allow to create custom animation of opening FileManager
         * @param animationOptions take an instance of [ActivityOptionsCompat]
         */
        fun setAnimationOptions(animationOptions: ActivityOptionsCompat): Builder {
            fileConfig.animationOptions = animationOptions
            return this
        }

        /**
         * Default is true
         * @param allow changes the behaviour of picking
         */
        fun allowMultipleFiles(allow: Boolean): Builder {
            fileConfig.allowMultiple = allow
            return this
        }

        /**
         * @return Instance of [FilePicker]
         */
        fun build(): FilePicker {
            return FilePicker(componentActivity, fileConfig)
        }
    }

    data class Config(
        var allowMultiple: Boolean = true,
        var type: List<String> = listOf("*/*"),
        var animationOptions: ActivityOptionsCompat? = null
    )

    abstract class FilePickerResultLauncher(
        private val componentActivity: ComponentActivity,
        private val fileConfig: Config
    ) {

        /**
         * @param mimeType is accept many parameters of type [String],
         * ### Example:
         * ```
         *      setMimeTypes(MimeType.IMAGE)
         * ```
         * #### OR
         * ```
         *      setMimeTypes(MimeType.IMAGE, MimeType.DOC_PDF, ....etc)
         * ```
         */
        fun launch(vararg mimeType: MimeType) {
            register(mimeType.map { it.value })
        }

        /**
         * @param mimeType is accept many parameters of type [String],
         * ### Example:
         * ```
         *      setMimeTypes("image/png")
         * ```
         * #### OR
         * ```
         *      setMimeTypes("image/jpeg", "application/pdf", ....etc)
         * ```
         */
        fun launch(vararg mimeType: String) {
            register(mimeType.asList())
        }


        /**
         *
         * @param mimeType is accept many parameters of type [MimeType],
         * ### Example:
         * ```
         *      setMimeTypes(MimeType.IMAGE)
         * ```
         * #### OR
         * ```
         *      setMimeTypes(MimeType.IMAGE, MimeType.DOC_PDF, ....etc)
         * ```
         */
        fun launch(mimeType: List<String>) {
            register(mimeType)
        }

        private fun register(mimeType: List<String>) {
            componentActivity.activityResultRegistry.register(
                "#Picker_rq",
                FilePickerContent(
                    componentActivity.applicationContext,
                    fileConfig
                )
            ) {
                onFilesPicked(it)
            }.launch(mimeType, fileConfig.animationOptions)
        }

        protected abstract fun onFilesPicked(list: List<UriData>)
    }

    fun interface OnFilePickedListener {
        /**
         * It takes callback when user select some of files from FileManager
         * @param list contains [UriData] which have [Uri][UriData.uri], and [MimeType][UriData.mimeType]
         */
        fun onFilePicked(list: List<UriData>)
    }
}