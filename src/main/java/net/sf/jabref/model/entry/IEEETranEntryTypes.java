begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

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
name|database
operator|.
name|BibtexDatabase
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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

begin_comment
comment|/**  * This class represents all supported IEEETran entry types.  * @see http://ctan.sharelatex.com/tex-archive/macros/latex/contrib/IEEEtran/bibtex/IEEEtran_bst_HOWTO.pdf  *  * Electronic, IEEETranBSTCTL, Periodical, Patent, Standard  */
end_comment

begin_class
DECL|class|IEEETranEntryTypes
specifier|public
class|class
name|IEEETranEntryTypes
block|{
comment|/**      * Electronic entry type for internet references      *      * Required fields:      * Optional fields: author, month, year, title, language, howpublished, organization, address, note, url      */
DECL|field|ELECTRONIC
specifier|public
specifier|static
specifier|final
name|EntryType
name|ELECTRONIC
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllOptional
argument_list|(
literal|"author"
argument_list|,
literal|"month"
argument_list|,
literal|"year"
argument_list|,
literal|"title"
argument_list|,
literal|"language"
argument_list|,
literal|"howpublished"
argument_list|,
literal|"organization"
argument_list|,
literal|"address"
argument_list|,
literal|"note"
argument_list|,
literal|"url"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Electronic"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"bibtexkey"
block|}
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
comment|/**      * Special entry type that can be used to externally control some aspects of the bibliography style.      */
DECL|field|IEEETRANBSTCTL
specifier|public
specifier|static
specifier|final
name|EntryType
name|IEEETRANBSTCTL
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllOptional
argument_list|(
literal|"ctluse_article_number"
argument_list|,
literal|"ctluse_paper"
argument_list|,
literal|"ctluse_forced_etal"
argument_list|,
literal|"ctluse_url"
argument_list|,
literal|"ctlmax_names_forced_etal"
argument_list|,
literal|"ctlnames_show_etal"
argument_list|,
literal|"ctluse_alt_spacing"
argument_list|,
literal|"ctlalt_stretch_factor"
argument_list|,
literal|"ctldash_repeated_names"
argument_list|,
literal|"ctlname_format_string"
argument_list|,
literal|"ctlname_latex_cmd"
argument_list|,
literal|"ctlname_url_prefix"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"IEEEtranBSTCTL"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
block|}
decl_stmt|;
comment|/**      * The periodical entry type is used for journals and magazines.      *      * Required fields: title, year      * Optional fields: editor, language, series, volume, number, organization, month, note, url      */
DECL|field|PERIODICAL
specifier|public
specifier|static
specifier|final
name|EntryType
name|PERIODICAL
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllOptional
argument_list|(
literal|"editor"
argument_list|,
literal|"language"
argument_list|,
literal|"series"
argument_list|,
literal|"volume"
argument_list|,
literal|"number"
argument_list|,
literal|"organization"
argument_list|,
literal|"month"
argument_list|,
literal|"note"
argument_list|,
literal|"url"
argument_list|)
expr_stmt|;
name|addAllRequired
argument_list|(
literal|"title"
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Periodical"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"title"
block|,
literal|"year"
block|,
literal|"bibtexkey"
block|}
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
comment|/**      * Entry type for patents.      *      * Required fields: nationality, number, year or yearfiled      * Optional fields: author, title, language, assignee, address, type, number, day, dayfiled, month, monthfiled, note, url      */
DECL|field|PATENT
specifier|public
specifier|static
specifier|final
name|EntryType
name|PATENT
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllOptional
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"language"
argument_list|,
literal|"assignee"
argument_list|,
literal|"address"
argument_list|,
literal|"type"
argument_list|,
literal|"number"
argument_list|,
literal|"day"
argument_list|,
literal|"dayfiled"
argument_list|,
literal|"month"
argument_list|,
literal|"monthfiled"
argument_list|,
literal|"note"
argument_list|,
literal|"url"
argument_list|)
expr_stmt|;
name|addAllRequired
argument_list|(
literal|"nationality"
argument_list|,
literal|"number"
argument_list|,
literal|"year"
argument_list|,
literal|"yearfiled"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Patent"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"year|yearfiled"
block|,
literal|"number"
block|,
literal|"bibtexkey"
block|}
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
comment|/**      * The standard entry type is used for proposed or formally published standards.      *      * Required fields: title, organization or institution      * Optional fields: author, language, howpublished, type, number, revision, address, month, year, note, url      */
DECL|field|STANDARD
specifier|public
specifier|static
specifier|final
name|EntryType
name|STANDARD
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|requiredFieldsForCustomization
init|=
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"title"
block|,
literal|"organization/institution"
block|}
argument_list|)
argument_list|)
decl_stmt|;
block|{
name|addAllOptional
argument_list|(
literal|"author"
argument_list|,
literal|"language"
argument_list|,
literal|"howpublished"
argument_list|,
literal|"type"
argument_list|,
literal|"number"
argument_list|,
literal|"revision"
argument_list|,
literal|"address"
argument_list|,
literal|"month"
argument_list|,
literal|"year"
argument_list|,
literal|"note"
argument_list|,
literal|"url"
argument_list|)
expr_stmt|;
name|addAllRequired
argument_list|(
literal|"title"
argument_list|,
literal|"organization"
argument_list|,
literal|"institution"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Standard"
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getRequiredFieldsForCustomization
parameter_list|()
block|{
return|return
name|requiredFieldsForCustomization
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"organization|institution"
block|,
literal|"title"
block|,
literal|"bibtexkey"
block|}
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|ALL
specifier|public
specifier|static
specifier|final
name|List
argument_list|<
name|EntryType
argument_list|>
name|ALL
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|ELECTRONIC
argument_list|,
name|IEEETRANBSTCTL
argument_list|,
name|PERIODICAL
argument_list|,
name|PATENT
argument_list|,
name|STANDARD
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

