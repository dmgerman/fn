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
name|List
import|;
end_import

begin_comment
comment|/**  * This class represents all supported IEEETran entry types.  *  * @see http://ctan.sharelatex.com/tex-archive/macros/latex/contrib/IEEEtran/bibtex/IEEEtran_bst_HOWTO.pdf  *<p>  * Electronic, IEEETranBSTCTL, Periodical, Patent, Standard  */
end_comment

begin_class
DECL|class|IEEETranEntryTypes
specifier|public
class|class
name|IEEETranEntryTypes
block|{
comment|/**      * Electronic entry type for internet references      *<p>      * Required fields:      * Optional fields: author, month, year, title, language, howpublished, organization, address, note, url      */
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
parameter_list|(
name|FieldName
operator|.
name|AUTHOR
parameter_list|,
name|FieldName
operator|.
name|MONTH
parameter_list|,
name|FieldName
operator|.
name|YEAR
parameter_list|,
name|FieldName
operator|.
name|TITLE
parameter_list|,
name|FieldName
operator|.
name|LANGUAGE
parameter_list|,
name|FieldName
operator|.
name|HOWPUBLISHED
parameter_list|,
name|FieldName
operator|.
name|ORGANIZATION
parameter_list|,
name|FieldName
operator|.
name|ADDRESS
parameter_list|,
name|FieldName
operator|.
name|NOTE
parameter_list|,
name|FieldName
operator|.
name|URL
parameter_list|)
constructor_decl|;
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
parameter_list|(
name|FieldName
operator|.
name|CTLUSE_ARTICLE_NUMBER
parameter_list|,
name|FieldName
operator|.
name|CTLUSE_PAPER
parameter_list|,
name|FieldName
operator|.
name|CTLUSE_FORCED_ETAL
parameter_list|,
name|FieldName
operator|.
name|CTLUSE_URL
parameter_list|,
name|FieldName
operator|.
name|CTLMAX_NAMES_FORCED_ETAL
parameter_list|,
name|FieldName
operator|.
name|CTLNAMES_SHOW_ETAL
parameter_list|,
name|FieldName
operator|.
name|CTLUSE_ALT_SPACING
parameter_list|,
name|FieldName
operator|.
name|CTLALT_STRETCH_FACTOR
parameter_list|,
name|FieldName
operator|.
name|CTLDASH_REPEATED_NAMES
parameter_list|,
name|FieldName
operator|.
name|CTLNAME_FORMAT_STRING
parameter_list|,
name|FieldName
operator|.
name|CTLNAME_LATEX_CMD
parameter_list|,
name|FieldName
operator|.
name|CTLNAME_URL_PREFIX
parameter_list|)
constructor_decl|;
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
block|}
decl_stmt|;
comment|/**      * The periodical entry type is used for journals and magazines.      *<p>      * Required fields: title, year      * Optional fields: editor, language, series, volume, number, organization, month, note, url      */
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
name|addAllRequired
parameter_list|(
name|FieldName
operator|.
name|TITLE
parameter_list|,
name|FieldName
operator|.
name|YEAR
parameter_list|)
constructor_decl|;
name|addAllOptional
parameter_list|(
name|FieldName
operator|.
name|EDITOR
parameter_list|,
name|FieldName
operator|.
name|LANGUAGE
parameter_list|,
name|FieldName
operator|.
name|SERIES
parameter_list|,
name|FieldName
operator|.
name|VOLUME
parameter_list|,
name|FieldName
operator|.
name|NUMBER
parameter_list|,
name|FieldName
operator|.
name|ORGANIZATION
parameter_list|,
name|FieldName
operator|.
name|MONTH
parameter_list|,
name|FieldName
operator|.
name|NOTE
parameter_list|,
name|FieldName
operator|.
name|URL
parameter_list|)
constructor_decl|;
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
block|}
decl_stmt|;
comment|/**      * Entry type for patents.      *<p>      * Required fields: nationality, number, year or yearfiled      * Optional fields: author, title, language, assignee, address, type, number, day, dayfiled, month, monthfiled, note, url      */
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
name|addAllRequired
argument_list|(
name|FieldName
operator|.
name|NATIONALITY
argument_list|,
name|FieldName
operator|.
name|NUMBER
argument_list|,
name|FieldName
operator|.
name|orFields
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|,
name|FieldName
operator|.
name|YEARFILED
argument_list|)
argument_list|)
expr_stmt|;
name|addAllOptional
parameter_list|(
name|FieldName
operator|.
name|AUTHOR
parameter_list|,
name|FieldName
operator|.
name|TITLE
parameter_list|,
name|FieldName
operator|.
name|LANGUAGE
parameter_list|,
name|FieldName
operator|.
name|ASSIGNEE
parameter_list|,
name|FieldName
operator|.
name|ADDRESS
parameter_list|,
name|FieldName
operator|.
name|TYPE
parameter_list|,
name|FieldName
operator|.
name|NUMBER
parameter_list|,
name|FieldName
operator|.
name|DAY
parameter_list|,
name|FieldName
operator|.
name|DAYFILED
parameter_list|,
name|FieldName
operator|.
name|MONTH
parameter_list|,
name|FieldName
operator|.
name|MONTHFILED
parameter_list|,
name|FieldName
operator|.
name|NOTE
parameter_list|,
name|FieldName
operator|.
name|URL
parameter_list|)
constructor_decl|;
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
block|}
decl_stmt|;
comment|/**      * The standard entry type is used for proposed or formally published standards.      *<p>      * Required fields: title, organization or institution      * Optional fields: author, language, howpublished, type, number, revision, address, month, year, note, url      */
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
block|{
name|addAllRequired
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
name|FieldName
operator|.
name|orFields
argument_list|(
name|FieldName
operator|.
name|ORGANIZATION
argument_list|,
name|FieldName
operator|.
name|INSTITUTION
argument_list|)
argument_list|)
expr_stmt|;
name|addAllOptional
parameter_list|(
name|FieldName
operator|.
name|AUTHOR
parameter_list|,
name|FieldName
operator|.
name|LANGUAGE
parameter_list|,
name|FieldName
operator|.
name|HOWPUBLISHED
parameter_list|,
name|FieldName
operator|.
name|TYPE
parameter_list|,
name|FieldName
operator|.
name|NUMBER
parameter_list|,
name|FieldName
operator|.
name|REVISION
parameter_list|,
name|FieldName
operator|.
name|ADDRESS
parameter_list|,
name|FieldName
operator|.
name|MONTH
parameter_list|,
name|FieldName
operator|.
name|YEAR
parameter_list|,
name|FieldName
operator|.
name|NOTE
parameter_list|,
name|FieldName
operator|.
name|URL
parameter_list|)
constructor_decl|;
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

