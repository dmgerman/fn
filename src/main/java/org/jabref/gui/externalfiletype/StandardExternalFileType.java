begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.externalfiletype
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiletype
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|icon
operator|.
name|IconTheme
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|icon
operator|.
name|JabRefIcon
import|;
end_import

begin_import
import|import
name|org
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

begin_enum
DECL|enum|StandardExternalFileType
specifier|public
enum|enum
name|StandardExternalFileType
implements|implements
name|ExternalFileType
block|{
DECL|enumConstant|PDF
name|PDF
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
name|JabRefIcons
operator|.
name|PDF_FILE
argument_list|)
block|,
DECL|enumConstant|PostScript
name|PostScript
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
name|JabRefIcons
operator|.
name|FILE
argument_list|)
block|,
DECL|enumConstant|Word
name|Word
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
name|JabRefIcons
operator|.
name|FILE_WORD
argument_list|)
block|,
DECL|enumConstant|Word_NEW
name|Word_NEW
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
name|JabRefIcons
operator|.
name|FILE_WORD
argument_list|)
block|,
DECL|enumConstant|OpenDocument_TEXT
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.FILE_OPENOFFICE
name|OpenDocument_TEXT
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
name|JabRefIcons
operator|.
name|FILE_OPENOFFICE
argument_list|)
block|,
DECL|enumConstant|Excel
name|Excel
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
name|JabRefIcons
operator|.
name|FILE_EXCEL
argument_list|)
block|,
DECL|enumConstant|Excel_NEW
name|Excel_NEW
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
name|JabRefIcons
operator|.
name|FILE_EXCEL
argument_list|)
block|,
DECL|enumConstant|OpenDocumentSpreadsheet
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.FILE_OPENOFFICE
name|OpenDocumentSpreadsheet
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
name|JabRefIcons
operator|.
name|FILE_OPENOFFICE
argument_list|)
block|,
DECL|enumConstant|PowerPoint
name|PowerPoint
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
name|JabRefIcons
operator|.
name|FILE_POWERPOINT
argument_list|)
block|,
DECL|enumConstant|PowerPoint_NEW
name|PowerPoint_NEW
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
name|JabRefIcons
operator|.
name|FILE_POWERPOINT
argument_list|)
block|,
DECL|enumConstant|OpenDocumentPresentation
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.FILE_OPENOFFICE
name|OpenDocumentPresentation
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
name|JabRefIcons
operator|.
name|FILE_OPENOFFICE
argument_list|)
block|,
DECL|enumConstant|RTF
name|RTF
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
name|JabRefIcons
operator|.
name|FILE_TEXT
argument_list|)
block|,
DECL|enumConstant|PNG
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.PICTURE
name|PNG
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
name|JabRefIcons
operator|.
name|PICTURE
argument_list|)
block|,
DECL|enumConstant|GIF
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.PICTURE
name|GIF
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
name|JabRefIcons
operator|.
name|PICTURE
argument_list|)
block|,
DECL|enumConstant|JPG
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.PICTURE
name|JPG
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
name|JabRefIcons
operator|.
name|PICTURE
argument_list|)
block|,
DECL|enumConstant|Djvu
name|Djvu
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
name|JabRefIcons
operator|.
name|FILE
argument_list|)
block|,
DECL|enumConstant|TXT
name|TXT
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
name|JabRefIcons
operator|.
name|FILE_TEXT
argument_list|)
block|,
DECL|enumConstant|TEX
name|TEX
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
name|JabRefIcons
operator|.
name|FILE_TEXT
argument_list|)
block|,
DECL|enumConstant|CHM
name|CHM
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
name|JabRefIcons
operator|.
name|WWW
argument_list|)
block|,
DECL|enumConstant|TIFF
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.PICTURE
name|TIFF
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
name|JabRefIcons
operator|.
name|PICTURE
argument_list|)
block|,
DECL|enumConstant|URL
name|URL
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
name|JabRefIcons
operator|.
name|WWW
argument_list|)
block|,
DECL|enumConstant|MHT
name|MHT
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
name|JabRefIcons
operator|.
name|WWW
argument_list|)
block|,
DECL|enumConstant|ePUB
name|ePUB
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
name|JabRefIcons
operator|.
name|WWW
argument_list|)
block|;
DECL|field|name
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
DECL|field|extension
specifier|private
specifier|final
name|String
name|extension
decl_stmt|;
DECL|field|mimeType
specifier|private
specifier|final
name|String
name|mimeType
decl_stmt|;
DECL|field|openWith
specifier|private
specifier|final
name|String
name|openWith
decl_stmt|;
DECL|field|iconName
specifier|private
specifier|final
name|String
name|iconName
decl_stmt|;
DECL|field|icon
specifier|private
specifier|final
name|JabRefIcon
name|icon
decl_stmt|;
DECL|method|StandardExternalFileType (String name, String extension, String mimeType, String openWith, String iconName, JabRefIcon icon)
name|StandardExternalFileType
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|extension
parameter_list|,
name|String
name|mimeType
parameter_list|,
name|String
name|openWith
parameter_list|,
name|String
name|iconName
parameter_list|,
name|JabRefIcon
name|icon
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|extension
operator|=
name|extension
expr_stmt|;
name|this
operator|.
name|mimeType
operator|=
name|mimeType
expr_stmt|;
name|this
operator|.
name|openWith
operator|=
name|openWith
expr_stmt|;
name|this
operator|.
name|iconName
operator|=
name|iconName
expr_stmt|;
name|this
operator|.
name|icon
operator|=
name|icon
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
annotation|@
name|Override
DECL|method|getExtension ()
specifier|public
name|String
name|getExtension
parameter_list|()
block|{
return|return
name|extension
return|;
block|}
annotation|@
name|Override
DECL|method|getMimeType ()
specifier|public
name|String
name|getMimeType
parameter_list|()
block|{
return|return
name|mimeType
return|;
block|}
annotation|@
name|Override
DECL|method|getOpenWithApplication ()
specifier|public
name|String
name|getOpenWithApplication
parameter_list|()
block|{
comment|// On all OSes there is a generic application available to handle file opening, so use this one
return|return
literal|""
return|;
block|}
annotation|@
name|Override
DECL|method|getIcon ()
specifier|public
name|JabRefIcon
name|getIcon
parameter_list|()
block|{
return|return
name|icon
return|;
block|}
block|}
end_enum

end_unit

