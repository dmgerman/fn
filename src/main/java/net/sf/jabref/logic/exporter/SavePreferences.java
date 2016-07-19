begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|exporter
package|;
end_package

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|logic
operator|.
name|config
operator|.
name|SaveOrderConfig
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|SavePreferences
specifier|public
class|class
name|SavePreferences
block|{
DECL|field|reformatFile
specifier|private
specifier|final
name|boolean
name|reformatFile
decl_stmt|;
DECL|field|saveInOriginalOrder
specifier|private
specifier|final
name|boolean
name|saveInOriginalOrder
decl_stmt|;
DECL|field|saveOrder
specifier|private
specifier|final
name|SaveOrderConfig
name|saveOrder
decl_stmt|;
DECL|field|encoding
specifier|private
specifier|final
name|Charset
name|encoding
decl_stmt|;
DECL|field|makeBackup
specifier|private
specifier|final
name|boolean
name|makeBackup
decl_stmt|;
DECL|field|saveType
specifier|private
specifier|final
name|DatabaseSaveType
name|saveType
decl_stmt|;
DECL|field|takeMetadataSaveOrderInAccount
specifier|private
specifier|final
name|boolean
name|takeMetadataSaveOrderInAccount
decl_stmt|;
DECL|method|SavePreferences ()
specifier|public
name|SavePreferences
parameter_list|()
block|{
name|this
argument_list|(
literal|true
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|,
name|DatabaseSaveType
operator|.
name|ALL
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|SavePreferences (Boolean saveInOriginalOrder, SaveOrderConfig saveOrder, Charset encoding, Boolean makeBackup, DatabaseSaveType saveType, Boolean takeMetadataSaveOrderInAccount, Boolean reformatFile)
specifier|public
name|SavePreferences
parameter_list|(
name|Boolean
name|saveInOriginalOrder
parameter_list|,
name|SaveOrderConfig
name|saveOrder
parameter_list|,
name|Charset
name|encoding
parameter_list|,
name|Boolean
name|makeBackup
parameter_list|,
name|DatabaseSaveType
name|saveType
parameter_list|,
name|Boolean
name|takeMetadataSaveOrderInAccount
parameter_list|,
name|Boolean
name|reformatFile
parameter_list|)
block|{
name|this
operator|.
name|saveInOriginalOrder
operator|=
name|saveInOriginalOrder
expr_stmt|;
name|this
operator|.
name|saveOrder
operator|=
name|saveOrder
expr_stmt|;
name|this
operator|.
name|encoding
operator|=
name|encoding
expr_stmt|;
name|this
operator|.
name|makeBackup
operator|=
name|makeBackup
expr_stmt|;
name|this
operator|.
name|saveType
operator|=
name|saveType
expr_stmt|;
name|this
operator|.
name|takeMetadataSaveOrderInAccount
operator|=
name|takeMetadataSaveOrderInAccount
expr_stmt|;
name|this
operator|.
name|reformatFile
operator|=
name|reformatFile
expr_stmt|;
block|}
DECL|method|loadForExportFromPreferences (JabRefPreferences preferences)
specifier|public
specifier|static
name|SavePreferences
name|loadForExportFromPreferences
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|Boolean
name|saveInOriginalOrder
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_IN_ORIGINAL_ORDER
argument_list|)
decl_stmt|;
name|SaveOrderConfig
name|saveOrder
init|=
literal|null
decl_stmt|;
if|if
condition|(
operator|!
name|saveInOriginalOrder
condition|)
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_IN_SPECIFIED_ORDER
argument_list|)
condition|)
block|{
name|saveOrder
operator|=
name|SaveOrderConfig
operator|.
name|loadExportSaveOrderFromPreferences
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|saveOrder
operator|=
name|SaveOrderConfig
operator|.
name|loadTableSaveOrderFromPreferences
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
block|}
block|}
name|Charset
name|encoding
init|=
name|preferences
operator|.
name|getDefaultEncoding
argument_list|()
decl_stmt|;
name|Boolean
name|makeBackup
init|=
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BACKUP
argument_list|)
decl_stmt|;
name|DatabaseSaveType
name|saveType
init|=
name|DatabaseSaveType
operator|.
name|ALL
decl_stmt|;
name|Boolean
name|takeMetadataSaveOrderInAccount
init|=
literal|false
decl_stmt|;
name|Boolean
name|reformatFile
init|=
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|REFORMAT_FILE_ON_SAVE_AND_EXPORT
argument_list|)
decl_stmt|;
return|return
operator|new
name|SavePreferences
argument_list|(
name|saveInOriginalOrder
argument_list|,
name|saveOrder
argument_list|,
name|encoding
argument_list|,
name|makeBackup
argument_list|,
name|saveType
argument_list|,
name|takeMetadataSaveOrderInAccount
argument_list|,
name|reformatFile
argument_list|)
return|;
block|}
DECL|method|loadForSaveFromPreferences (JabRefPreferences preferences)
specifier|public
specifier|static
name|SavePreferences
name|loadForSaveFromPreferences
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|Boolean
name|saveInOriginalOrder
init|=
literal|false
decl_stmt|;
name|SaveOrderConfig
name|saveOrder
init|=
literal|null
decl_stmt|;
name|Charset
name|encoding
init|=
name|preferences
operator|.
name|getDefaultEncoding
argument_list|()
decl_stmt|;
name|Boolean
name|makeBackup
init|=
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BACKUP
argument_list|)
decl_stmt|;
name|DatabaseSaveType
name|saveType
init|=
name|DatabaseSaveType
operator|.
name|ALL
decl_stmt|;
name|Boolean
name|takeMetadataSaveOrderInAccount
init|=
literal|true
decl_stmt|;
name|Boolean
name|reformatFile
init|=
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|REFORMAT_FILE_ON_SAVE_AND_EXPORT
argument_list|)
decl_stmt|;
return|return
operator|new
name|SavePreferences
argument_list|(
name|saveInOriginalOrder
argument_list|,
name|saveOrder
argument_list|,
name|encoding
argument_list|,
name|makeBackup
argument_list|,
name|saveType
argument_list|,
name|takeMetadataSaveOrderInAccount
argument_list|,
name|reformatFile
argument_list|)
return|;
block|}
DECL|method|getTakeMetadataSaveOrderInAccount ()
specifier|public
name|Boolean
name|getTakeMetadataSaveOrderInAccount
parameter_list|()
block|{
return|return
name|takeMetadataSaveOrderInAccount
return|;
block|}
DECL|method|getSaveOrder ()
specifier|public
name|SaveOrderConfig
name|getSaveOrder
parameter_list|()
block|{
return|return
name|saveOrder
return|;
block|}
DECL|method|isSaveInOriginalOrder ()
specifier|public
name|boolean
name|isSaveInOriginalOrder
parameter_list|()
block|{
return|return
name|saveInOriginalOrder
return|;
block|}
DECL|method|withSaveInOriginalOrder (Boolean saveInOriginalOrder)
specifier|public
name|SavePreferences
name|withSaveInOriginalOrder
parameter_list|(
name|Boolean
name|saveInOriginalOrder
parameter_list|)
block|{
return|return
operator|new
name|SavePreferences
argument_list|(
name|saveInOriginalOrder
argument_list|,
name|this
operator|.
name|saveOrder
argument_list|,
name|this
operator|.
name|encoding
argument_list|,
name|this
operator|.
name|makeBackup
argument_list|,
name|this
operator|.
name|saveType
argument_list|,
name|this
operator|.
name|takeMetadataSaveOrderInAccount
argument_list|,
name|this
operator|.
name|reformatFile
argument_list|)
return|;
block|}
DECL|method|getMakeBackup ()
specifier|public
name|boolean
name|getMakeBackup
parameter_list|()
block|{
return|return
name|makeBackup
return|;
block|}
DECL|method|withMakeBackup (Boolean makeBackup)
specifier|public
name|SavePreferences
name|withMakeBackup
parameter_list|(
name|Boolean
name|makeBackup
parameter_list|)
block|{
return|return
operator|new
name|SavePreferences
argument_list|(
name|this
operator|.
name|saveInOriginalOrder
argument_list|,
name|this
operator|.
name|saveOrder
argument_list|,
name|this
operator|.
name|encoding
argument_list|,
name|makeBackup
argument_list|,
name|this
operator|.
name|saveType
argument_list|,
name|this
operator|.
name|takeMetadataSaveOrderInAccount
argument_list|,
name|this
operator|.
name|reformatFile
argument_list|)
return|;
block|}
DECL|method|getEncoding ()
specifier|public
name|Charset
name|getEncoding
parameter_list|()
block|{
return|return
name|encoding
return|;
block|}
DECL|method|withEncoding (Charset encoding)
specifier|public
name|SavePreferences
name|withEncoding
parameter_list|(
name|Charset
name|encoding
parameter_list|)
block|{
return|return
operator|new
name|SavePreferences
argument_list|(
name|this
operator|.
name|saveInOriginalOrder
argument_list|,
name|this
operator|.
name|saveOrder
argument_list|,
name|encoding
argument_list|,
name|this
operator|.
name|makeBackup
argument_list|,
name|this
operator|.
name|saveType
argument_list|,
name|this
operator|.
name|takeMetadataSaveOrderInAccount
argument_list|,
name|this
operator|.
name|reformatFile
argument_list|)
return|;
block|}
DECL|method|getSaveType ()
specifier|public
name|DatabaseSaveType
name|getSaveType
parameter_list|()
block|{
return|return
name|saveType
return|;
block|}
DECL|method|withSaveType (DatabaseSaveType saveType)
specifier|public
name|SavePreferences
name|withSaveType
parameter_list|(
name|DatabaseSaveType
name|saveType
parameter_list|)
block|{
return|return
operator|new
name|SavePreferences
argument_list|(
name|this
operator|.
name|saveInOriginalOrder
argument_list|,
name|this
operator|.
name|saveOrder
argument_list|,
name|this
operator|.
name|encoding
argument_list|,
name|this
operator|.
name|makeBackup
argument_list|,
name|saveType
argument_list|,
name|this
operator|.
name|takeMetadataSaveOrderInAccount
argument_list|,
name|this
operator|.
name|reformatFile
argument_list|)
return|;
block|}
DECL|method|isReformatFile ()
specifier|public
name|Boolean
name|isReformatFile
parameter_list|()
block|{
return|return
name|reformatFile
return|;
block|}
DECL|method|withReformatFile (boolean reformatFile)
specifier|public
name|SavePreferences
name|withReformatFile
parameter_list|(
name|boolean
name|reformatFile
parameter_list|)
block|{
return|return
operator|new
name|SavePreferences
argument_list|(
name|this
operator|.
name|saveInOriginalOrder
argument_list|,
name|this
operator|.
name|saveOrder
argument_list|,
name|this
operator|.
name|encoding
argument_list|,
name|this
operator|.
name|makeBackup
argument_list|,
name|this
operator|.
name|saveType
argument_list|,
name|this
operator|.
name|takeMetadataSaveOrderInAccount
argument_list|,
name|reformatFile
argument_list|)
return|;
block|}
DECL|method|getEncodingOrDefault ()
specifier|public
name|Charset
name|getEncodingOrDefault
parameter_list|()
block|{
return|return
name|encoding
operator|==
literal|null
condition|?
name|Charset
operator|.
name|defaultCharset
argument_list|()
else|:
name|encoding
return|;
block|}
DECL|enum|DatabaseSaveType
specifier|public
enum|enum
name|DatabaseSaveType
block|{
DECL|enumConstant|ALL
name|ALL
block|,
DECL|enumConstant|PLAIN_BIBTEX
name|PLAIN_BIBTEX
block|}
block|}
end_class

end_unit

