begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
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
name|java
operator|.
name|util
operator|.
name|Set
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
name|bibtex
operator|.
name|FieldContentParserPreferences
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
name|bibtexkeypattern
operator|.
name|BibtexKeyPatternPreferences
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
name|importer
operator|.
name|fileformat
operator|.
name|CustomImporter
import|;
end_import

begin_class
DECL|class|ImportFormatPreferences
specifier|public
class|class
name|ImportFormatPreferences
block|{
DECL|field|customImportList
specifier|private
specifier|final
name|Set
argument_list|<
name|CustomImporter
argument_list|>
name|customImportList
decl_stmt|;
DECL|field|encoding
specifier|private
specifier|final
name|Charset
name|encoding
decl_stmt|;
DECL|field|keywordSeparator
specifier|private
specifier|final
name|Character
name|keywordSeparator
decl_stmt|;
DECL|field|bibtexKeyPatternPreferences
specifier|private
specifier|final
name|BibtexKeyPatternPreferences
name|bibtexKeyPatternPreferences
decl_stmt|;
DECL|field|fieldContentParserPreferences
specifier|private
specifier|final
name|FieldContentParserPreferences
name|fieldContentParserPreferences
decl_stmt|;
DECL|field|keywordSyncEnabled
specifier|private
specifier|final
name|boolean
name|keywordSyncEnabled
decl_stmt|;
DECL|method|ImportFormatPreferences (Set<CustomImporter> customImportList, Charset encoding, Character keywordSeparator, BibtexKeyPatternPreferences bibtexKeyPatternPreferences, FieldContentParserPreferences fieldContentParserPreferences, boolean keywordSyncEnabled)
specifier|public
name|ImportFormatPreferences
parameter_list|(
name|Set
argument_list|<
name|CustomImporter
argument_list|>
name|customImportList
parameter_list|,
name|Charset
name|encoding
parameter_list|,
name|Character
name|keywordSeparator
parameter_list|,
name|BibtexKeyPatternPreferences
name|bibtexKeyPatternPreferences
parameter_list|,
name|FieldContentParserPreferences
name|fieldContentParserPreferences
parameter_list|,
name|boolean
name|keywordSyncEnabled
parameter_list|)
block|{
name|this
operator|.
name|customImportList
operator|=
name|customImportList
expr_stmt|;
name|this
operator|.
name|encoding
operator|=
name|encoding
expr_stmt|;
name|this
operator|.
name|keywordSeparator
operator|=
name|keywordSeparator
expr_stmt|;
name|this
operator|.
name|bibtexKeyPatternPreferences
operator|=
name|bibtexKeyPatternPreferences
expr_stmt|;
name|this
operator|.
name|fieldContentParserPreferences
operator|=
name|fieldContentParserPreferences
expr_stmt|;
name|this
operator|.
name|keywordSyncEnabled
operator|=
name|keywordSyncEnabled
expr_stmt|;
block|}
comment|/**      * @deprecated importer should not know about the other custom importers      */
annotation|@
name|Deprecated
DECL|method|getCustomImportList ()
specifier|public
name|Set
argument_list|<
name|CustomImporter
argument_list|>
name|getCustomImportList
parameter_list|()
block|{
return|return
name|customImportList
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
DECL|method|getKeywordSeparator ()
specifier|public
name|Character
name|getKeywordSeparator
parameter_list|()
block|{
return|return
name|keywordSeparator
return|;
block|}
DECL|method|getBibtexKeyPatternPreferences ()
specifier|public
name|BibtexKeyPatternPreferences
name|getBibtexKeyPatternPreferences
parameter_list|()
block|{
return|return
name|bibtexKeyPatternPreferences
return|;
block|}
DECL|method|getFieldContentParserPreferences ()
specifier|public
name|FieldContentParserPreferences
name|getFieldContentParserPreferences
parameter_list|()
block|{
return|return
name|fieldContentParserPreferences
return|;
block|}
DECL|method|withEncoding (Charset newEncoding)
specifier|public
name|ImportFormatPreferences
name|withEncoding
parameter_list|(
name|Charset
name|newEncoding
parameter_list|)
block|{
return|return
operator|new
name|ImportFormatPreferences
argument_list|(
name|customImportList
argument_list|,
name|newEncoding
argument_list|,
name|keywordSeparator
argument_list|,
name|bibtexKeyPatternPreferences
argument_list|,
name|fieldContentParserPreferences
argument_list|,
name|keywordSyncEnabled
argument_list|)
return|;
block|}
comment|/**      * @deprecated importer should not keyword synchronization; this is a post-import action      */
annotation|@
name|Deprecated
DECL|method|isKeywordSyncEnabled ()
specifier|public
name|boolean
name|isKeywordSyncEnabled
parameter_list|()
block|{
return|return
name|keywordSyncEnabled
return|;
block|}
block|}
end_class

end_unit

