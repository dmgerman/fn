begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|IconTheme
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|FileField
import|;
end_import

begin_class
DECL|class|ExternalFileTypes
specifier|public
specifier|final
class|class
name|ExternalFileTypes
block|{
comment|// This String is used in the encoded list in prefs of external file type
comment|// modifications, in order to indicate a removed default file type:
DECL|field|FILE_TYPE_REMOVED_FLAG
specifier|private
specifier|static
specifier|final
name|String
name|FILE_TYPE_REMOVED_FLAG
init|=
literal|"REMOVED"
decl_stmt|;
comment|// Map containing all registered external file types:
DECL|field|externalFileTypes
specifier|private
specifier|final
name|Set
argument_list|<
name|ExternalFileType
argument_list|>
name|externalFileTypes
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|HTML_FALLBACK_TYPE
specifier|private
specifier|final
name|ExternalFileType
name|HTML_FALLBACK_TYPE
init|=
operator|new
name|ExternalFileType
argument_list|(
literal|"URL"
argument_list|,
literal|"html"
argument_list|,
literal|"text/html"
argument_list|,
literal|""
argument_list|,
literal|"www"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|WWW
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
decl_stmt|;
comment|// The only instance of this class:
DECL|field|singleton
specifier|private
specifier|static
name|ExternalFileTypes
name|singleton
decl_stmt|;
DECL|method|getInstance ()
specifier|public
specifier|static
name|ExternalFileTypes
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|ExternalFileTypes
operator|.
name|singleton
operator|==
literal|null
condition|)
block|{
name|ExternalFileTypes
operator|.
name|singleton
operator|=
operator|new
name|ExternalFileTypes
argument_list|()
expr_stmt|;
block|}
return|return
name|ExternalFileTypes
operator|.
name|singleton
return|;
block|}
DECL|method|ExternalFileTypes ()
specifier|private
name|ExternalFileTypes
parameter_list|()
block|{
name|updateExternalFileTypes
argument_list|()
expr_stmt|;
block|}
DECL|method|getDefaultExternalFileTypes ()
specifier|public
specifier|static
name|List
argument_list|<
name|ExternalFileType
argument_list|>
name|getDefaultExternalFileTypes
parameter_list|()
block|{
name|List
argument_list|<
name|ExternalFileType
argument_list|>
name|list
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
literal|"PDF"
argument_list|,
literal|"pdf"
argument_list|,
literal|"application/pdf"
argument_list|,
literal|"evince"
argument_list|,
literal|"pdfSmall"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PDF_FILE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
literal|"PostScript"
argument_list|,
literal|"ps"
argument_list|,
literal|"application/postscript"
argument_list|,
literal|"evince"
argument_list|,
literal|"psSmall"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
literal|"Word"
argument_list|,
literal|"doc"
argument_list|,
literal|"application/msword"
argument_list|,
literal|"oowriter"
argument_list|,
literal|"openoffice"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE_WORD
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
literal|"Word 2007+"
argument_list|,
literal|"docx"
argument_list|,
literal|"application/vnd.openxmlformats-officedocument.wordprocessingml.document"
argument_list|,
literal|"oowriter"
argument_list|,
literal|"openoffice"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE_WORD
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OpenDocument text"
argument_list|)
argument_list|,
literal|"odt"
argument_list|,
literal|"application/vnd.oasis.opendocument.text"
argument_list|,
literal|"oowriter"
argument_list|,
literal|"openoffice"
argument_list|,
name|IconTheme
operator|.
name|getImage
argument_list|(
literal|"openoffice"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
literal|"Excel"
argument_list|,
literal|"xls"
argument_list|,
literal|"application/excel"
argument_list|,
literal|"oocalc"
argument_list|,
literal|"openoffice"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE_EXCEL
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
literal|"Excel 2007+"
argument_list|,
literal|"xlsx"
argument_list|,
literal|"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
argument_list|,
literal|"oocalc"
argument_list|,
literal|"openoffice"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE_EXCEL
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OpenDocument spreadsheet"
argument_list|)
argument_list|,
literal|"ods"
argument_list|,
literal|"application/vnd.oasis.opendocument.spreadsheet"
argument_list|,
literal|"oocalc"
argument_list|,
literal|"openoffice"
argument_list|,
name|IconTheme
operator|.
name|getImage
argument_list|(
literal|"openoffice"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
literal|"PowerPoint"
argument_list|,
literal|"ppt"
argument_list|,
literal|"application/vnd.ms-powerpoint"
argument_list|,
literal|"ooimpress"
argument_list|,
literal|"openoffice"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE_POWERPOINT
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
literal|"PowerPoint 2007+"
argument_list|,
literal|"pptx"
argument_list|,
literal|"application/vnd.openxmlformats-officedocument.presentationml.presentation"
argument_list|,
literal|"ooimpress"
argument_list|,
literal|"openoffice"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE_POWERPOINT
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OpenDocument presentation"
argument_list|)
argument_list|,
literal|"odp"
argument_list|,
literal|"application/vnd.oasis.opendocument.presentation"
argument_list|,
literal|"ooimpress"
argument_list|,
literal|"openoffice"
argument_list|,
name|IconTheme
operator|.
name|getImage
argument_list|(
literal|"openoffice"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
literal|"Rich Text Format"
argument_list|,
literal|"rtf"
argument_list|,
literal|"application/rtf"
argument_list|,
literal|"oowriter"
argument_list|,
literal|"openoffice"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE_TEXT
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 image"
argument_list|,
literal|"PNG"
argument_list|)
argument_list|,
literal|"png"
argument_list|,
literal|"image/png"
argument_list|,
literal|"gimp"
argument_list|,
literal|"picture"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PICTURE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 image"
argument_list|,
literal|"GIF"
argument_list|)
argument_list|,
literal|"gif"
argument_list|,
literal|"image/gif"
argument_list|,
literal|"gimp"
argument_list|,
literal|"picture"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PICTURE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 image"
argument_list|,
literal|"JPG"
argument_list|)
argument_list|,
literal|"jpg"
argument_list|,
literal|"image/jpeg"
argument_list|,
literal|"gimp"
argument_list|,
literal|"picture"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PICTURE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
literal|"Djvu"
argument_list|,
literal|"djvu"
argument_list|,
literal|"image/vnd.djvu"
argument_list|,
literal|"evince"
argument_list|,
literal|"psSmall"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
literal|"Text"
argument_list|,
literal|"txt"
argument_list|,
literal|"text/plain"
argument_list|,
literal|"emacs"
argument_list|,
literal|"emacs"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE_TEXT
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
literal|"LaTeX"
argument_list|,
literal|"tex"
argument_list|,
literal|"application/x-latex"
argument_list|,
literal|"emacs"
argument_list|,
literal|"emacs"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE_TEXT
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
literal|"CHM"
argument_list|,
literal|"chm"
argument_list|,
literal|"application/mshelp"
argument_list|,
literal|"gnochm"
argument_list|,
literal|"www"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|WWW
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 image"
argument_list|,
literal|"TIFF"
argument_list|)
argument_list|,
literal|"tiff"
argument_list|,
literal|"image/tiff"
argument_list|,
literal|"gimp"
argument_list|,
literal|"picture"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PICTURE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
literal|"URL"
argument_list|,
literal|"html"
argument_list|,
literal|"text/html"
argument_list|,
literal|"firefox"
argument_list|,
literal|"www"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|WWW
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
literal|"MHT"
argument_list|,
literal|"mht"
argument_list|,
literal|"multipart/related"
argument_list|,
literal|"firefox"
argument_list|,
literal|"www"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|WWW
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileType
argument_list|(
literal|"ePUB"
argument_list|,
literal|"epub"
argument_list|,
literal|"application/epub+zip"
argument_list|,
literal|"firefox"
argument_list|,
literal|"www"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|WWW
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// On all OSes there is a generic application available to handle file opening,
comment|// so we don't need the default application settings anymore:
for|for
control|(
name|ExternalFileType
name|type
range|:
name|list
control|)
block|{
name|type
operator|.
name|setOpenWith
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
return|return
name|list
return|;
block|}
DECL|method|getExternalFileTypeSelection ()
specifier|public
name|Collection
argument_list|<
name|ExternalFileType
argument_list|>
name|getExternalFileTypeSelection
parameter_list|()
block|{
return|return
name|externalFileTypes
return|;
block|}
comment|/**      * Look up the external file type registered with this name, if any.      *      * @param name The file type name.      * @return The ExternalFileType registered, or null if none.      */
DECL|method|getExternalFileTypeByName (String name)
specifier|public
name|ExternalFileType
name|getExternalFileTypeByName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
for|for
control|(
name|ExternalFileType
name|type
range|:
name|externalFileTypes
control|)
block|{
if|if
condition|(
name|type
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
block|{
return|return
name|type
return|;
block|}
block|}
comment|// Return an instance that signifies an unknown file type:
return|return
operator|new
name|UnknownExternalFileType
argument_list|(
name|name
argument_list|)
return|;
block|}
comment|/**      * Check if there is an external file type registered with this name.      *      * @param name The file type name.      * @return true if there is an ExternalFileType with this name, false if not.      */
DECL|method|isExternalFileTypeName (String name)
specifier|public
name|Boolean
name|isExternalFileTypeName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
for|for
control|(
name|ExternalFileType
name|type
range|:
name|externalFileTypes
control|)
block|{
if|if
condition|(
name|type
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Look up the external file type registered for this extension, if any.      *      * @param extension The file extension.      * @return The ExternalFileType registered, or null if none.      */
DECL|method|getExternalFileTypeByExt (String extension)
specifier|public
name|ExternalFileType
name|getExternalFileTypeByExt
parameter_list|(
name|String
name|extension
parameter_list|)
block|{
for|for
control|(
name|ExternalFileType
name|type
range|:
name|externalFileTypes
control|)
block|{
if|if
condition|(
operator|(
name|type
operator|.
name|getExtension
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
name|type
operator|.
name|getExtension
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|extension
argument_list|)
condition|)
block|{
return|return
name|type
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/**      * Look up the external file type name registered for this extension, if any.      *      * @param extension The file extension.      * @return The name of the ExternalFileType registered, or null if none.      */
DECL|method|getExternalFileTypeNameByExt (String extension)
specifier|public
name|String
name|getExternalFileTypeNameByExt
parameter_list|(
name|String
name|extension
parameter_list|)
block|{
for|for
control|(
name|ExternalFileType
name|type
range|:
name|externalFileTypes
control|)
block|{
if|if
condition|(
operator|(
name|type
operator|.
name|getExtension
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
name|type
operator|.
name|getExtension
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|extension
argument_list|)
condition|)
block|{
return|return
name|type
operator|.
name|getName
argument_list|()
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/**      * Look up the external file type registered for this filename, if any.      *      * @param filename The name of the file whose type to look up.      * @return The ExternalFileType registered, or null if none.      */
DECL|method|getExternalFileTypeForName (String filename)
specifier|public
name|ExternalFileType
name|getExternalFileTypeForName
parameter_list|(
name|String
name|filename
parameter_list|)
block|{
name|int
name|longestFound
init|=
operator|-
literal|1
decl_stmt|;
name|ExternalFileType
name|foundType
init|=
literal|null
decl_stmt|;
for|for
control|(
name|ExternalFileType
name|type
range|:
name|externalFileTypes
control|)
block|{
if|if
condition|(
operator|(
name|type
operator|.
name|getExtension
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
name|filename
operator|.
name|toLowerCase
argument_list|()
operator|.
name|endsWith
argument_list|(
name|type
operator|.
name|getExtension
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|&&
operator|(
name|type
operator|.
name|getExtension
argument_list|()
operator|.
name|length
argument_list|()
operator|>
name|longestFound
operator|)
condition|)
block|{
name|longestFound
operator|=
name|type
operator|.
name|getExtension
argument_list|()
operator|.
name|length
argument_list|()
expr_stmt|;
name|foundType
operator|=
name|type
expr_stmt|;
block|}
block|}
return|return
name|foundType
return|;
block|}
comment|/**      * Look up the external file type registered for this MIME type, if any.      *      * @param mimeType The MIME type.      * @return The ExternalFileType registered, or null if none. For the mime type "text/html", a valid file type is      *         guaranteed to be returned.      */
DECL|method|getExternalFileTypeByMimeType (String mimeType)
specifier|public
name|ExternalFileType
name|getExternalFileTypeByMimeType
parameter_list|(
name|String
name|mimeType
parameter_list|)
block|{
for|for
control|(
name|ExternalFileType
name|type
range|:
name|externalFileTypes
control|)
block|{
if|if
condition|(
operator|(
name|type
operator|.
name|getMimeType
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
name|type
operator|.
name|getMimeType
argument_list|()
operator|.
name|equals
argument_list|(
name|mimeType
argument_list|)
condition|)
block|{
return|return
name|type
return|;
block|}
block|}
if|if
condition|(
literal|"text/html"
operator|.
name|equals
argument_list|(
name|mimeType
argument_list|)
condition|)
block|{
return|return
name|HTML_FALLBACK_TYPE
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Look up the external file type name registered for this MIME type, if any.      *      * @param mimeType The MIME type.      * @return The name of the ExternalFileType registered, or null if none. For the mime type "text/html", a valid file type name is      *         guaranteed to be returned.      */
DECL|method|getExternalFileTypeNameByMimeType (String mimeType)
specifier|public
name|String
name|getExternalFileTypeNameByMimeType
parameter_list|(
name|String
name|mimeType
parameter_list|)
block|{
for|for
control|(
name|ExternalFileType
name|type
range|:
name|externalFileTypes
control|)
block|{
if|if
condition|(
operator|(
name|type
operator|.
name|getMimeType
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
name|type
operator|.
name|getMimeType
argument_list|()
operator|.
name|equals
argument_list|(
name|mimeType
argument_list|)
condition|)
block|{
return|return
name|type
operator|.
name|getName
argument_list|()
return|;
block|}
block|}
if|if
condition|(
literal|"text/html"
operator|.
name|equals
argument_list|(
name|mimeType
argument_list|)
condition|)
block|{
return|return
name|HTML_FALLBACK_TYPE
operator|.
name|getName
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Reset the List of external file types after user customization.      *      * @param types The new List of external file types. This is the complete list, not just new entries.      */
DECL|method|setExternalFileTypes (List<ExternalFileType> types)
specifier|public
name|void
name|setExternalFileTypes
parameter_list|(
name|List
argument_list|<
name|ExternalFileType
argument_list|>
name|types
parameter_list|)
block|{
comment|// First find a list of the default types:
name|List
argument_list|<
name|ExternalFileType
argument_list|>
name|defTypes
init|=
name|getDefaultExternalFileTypes
argument_list|()
decl_stmt|;
comment|// Make a list of types that are unchanged:
name|List
argument_list|<
name|ExternalFileType
argument_list|>
name|unchanged
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|externalFileTypes
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|ExternalFileType
name|type
range|:
name|types
control|)
block|{
name|externalFileTypes
operator|.
name|add
argument_list|(
name|type
argument_list|)
expr_stmt|;
comment|// See if we can find a type with matching name in the default type list:
name|ExternalFileType
name|found
init|=
literal|null
decl_stmt|;
for|for
control|(
name|ExternalFileType
name|defType
range|:
name|defTypes
control|)
block|{
if|if
condition|(
name|defType
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|type
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
name|found
operator|=
name|defType
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|found
operator|!=
literal|null
condition|)
block|{
comment|// Found it! Check if it is an exact match, or if it has been customized:
if|if
condition|(
name|found
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|unchanged
operator|.
name|add
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// It was modified. Remove its entry from the defaults list, since
comment|// the type hasn't been removed:
name|defTypes
operator|.
name|remove
argument_list|(
name|found
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Go through unchanged types. Remove them from the ones that should be stored,
comment|// and from the list of defaults, since we don't need to mention these in prefs:
for|for
control|(
name|ExternalFileType
name|type
range|:
name|unchanged
control|)
block|{
name|defTypes
operator|.
name|remove
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|types
operator|.
name|remove
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
comment|// Now set up the array to write to prefs, containing all new types, all modified
comment|// types, and a flag denoting each default type that has been removed:
name|String
index|[]
index|[]
name|array
init|=
operator|new
name|String
index|[
name|types
operator|.
name|size
argument_list|()
operator|+
name|defTypes
operator|.
name|size
argument_list|()
index|]
index|[]
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|ExternalFileType
name|type
range|:
name|types
control|)
block|{
name|array
index|[
name|i
index|]
operator|=
name|type
operator|.
name|getStringArrayRepresentation
argument_list|()
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
for|for
control|(
name|ExternalFileType
name|type
range|:
name|defTypes
control|)
block|{
name|array
index|[
name|i
index|]
operator|=
operator|new
name|String
index|[]
block|{
name|type
operator|.
name|getName
argument_list|()
block|,
name|FILE_TYPE_REMOVED_FLAG
block|}
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
literal|"externalFileTypes"
argument_list|,
name|FileField
operator|.
name|encodeStringArray
argument_list|(
name|array
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Set up the list of external file types, either from default values, or from values recorded in Preferences.      */
DECL|method|updateExternalFileTypes ()
specifier|private
name|void
name|updateExternalFileTypes
parameter_list|()
block|{
comment|// First get a list of the default file types as a starting point:
name|List
argument_list|<
name|ExternalFileType
argument_list|>
name|types
init|=
name|getDefaultExternalFileTypes
argument_list|()
decl_stmt|;
comment|// If no changes have been stored, simply use the defaults:
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EXTERNAL_FILE_TYPES
argument_list|,
literal|null
argument_list|)
operator|==
literal|null
condition|)
block|{
name|externalFileTypes
operator|.
name|clear
argument_list|()
expr_stmt|;
name|externalFileTypes
operator|.
name|addAll
argument_list|(
name|types
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// Read the prefs information for file types:
name|String
index|[]
index|[]
name|vals
init|=
name|StringUtil
operator|.
name|decodeStringDoubleArray
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EXTERNAL_FILE_TYPES
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|String
index|[]
name|val
range|:
name|vals
control|)
block|{
if|if
condition|(
operator|(
name|val
operator|.
name|length
operator|==
literal|2
operator|)
operator|&&
name|val
index|[
literal|1
index|]
operator|.
name|equals
argument_list|(
name|FILE_TYPE_REMOVED_FLAG
argument_list|)
condition|)
block|{
comment|// This entry indicates that a default entry type should be removed:
name|ExternalFileType
name|toRemove
init|=
literal|null
decl_stmt|;
for|for
control|(
name|ExternalFileType
name|type
range|:
name|types
control|)
block|{
if|if
condition|(
name|type
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|val
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
name|toRemove
operator|=
name|type
expr_stmt|;
break|break;
block|}
block|}
comment|// If we found it, remove it from the type list:
if|if
condition|(
name|toRemove
operator|!=
literal|null
condition|)
block|{
name|types
operator|.
name|remove
argument_list|(
name|toRemove
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// A new or modified entry type. Construct it from the string array:
name|ExternalFileType
name|type
init|=
name|ExternalFileType
operator|.
name|buildFromArgs
argument_list|(
name|val
argument_list|)
decl_stmt|;
comment|// Check if there is a default type with the same name. If so, this is a
comment|// modification of that type, so remove the default one:
name|ExternalFileType
name|toRemove
init|=
literal|null
decl_stmt|;
for|for
control|(
name|ExternalFileType
name|defType
range|:
name|types
control|)
block|{
if|if
condition|(
name|type
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|defType
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
name|toRemove
operator|=
name|defType
expr_stmt|;
break|break;
block|}
block|}
comment|// If we found it, remove it from the type list:
if|if
condition|(
name|toRemove
operator|!=
literal|null
condition|)
block|{
name|types
operator|.
name|remove
argument_list|(
name|toRemove
argument_list|)
expr_stmt|;
block|}
comment|// Then add the new one:
name|types
operator|.
name|add
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Finally, build the list of types based on the modified defaults list:
for|for
control|(
name|ExternalFileType
name|type
range|:
name|types
control|)
block|{
name|externalFileTypes
operator|.
name|add
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

