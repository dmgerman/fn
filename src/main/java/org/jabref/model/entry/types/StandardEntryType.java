begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry.types
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|types
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
import|;
end_import

begin_enum
DECL|enum|StandardEntryType
specifier|public
enum|enum
name|StandardEntryType
implements|implements
name|EntryType
block|{
comment|// BibTeX
DECL|enumConstant|Article
name|Article
argument_list|(
literal|"Article"
argument_list|)
block|,
DECL|enumConstant|Book
name|Book
argument_list|(
literal|"Book"
argument_list|)
block|,
DECL|enumConstant|Booklet
name|Booklet
argument_list|(
literal|"Booklet"
argument_list|)
block|,
DECL|enumConstant|Collection
name|Collection
argument_list|(
literal|"Collection"
argument_list|)
block|,
DECL|enumConstant|Conference
name|Conference
argument_list|(
literal|"Conference"
argument_list|)
block|,
DECL|enumConstant|InBook
name|InBook
argument_list|(
literal|"InBook"
argument_list|)
block|,
DECL|enumConstant|InCollection
name|InCollection
argument_list|(
literal|"InCollection"
argument_list|)
block|,
DECL|enumConstant|InProceedings
name|InProceedings
argument_list|(
literal|"InProceedings"
argument_list|)
block|,
DECL|enumConstant|Manual
name|Manual
argument_list|(
literal|"Manual"
argument_list|)
block|,
DECL|enumConstant|MastersThesis
name|MastersThesis
argument_list|(
literal|"MastersThesis"
argument_list|)
block|,
DECL|enumConstant|Misc
name|Misc
argument_list|(
literal|"Misc"
argument_list|)
block|,
DECL|enumConstant|PhdThesis
name|PhdThesis
argument_list|(
literal|"PhdThesis"
argument_list|)
block|,
DECL|enumConstant|Proceedings
name|Proceedings
argument_list|(
literal|"Proceedings"
argument_list|)
block|,
DECL|enumConstant|TechReport
name|TechReport
argument_list|(
literal|"TechReport"
argument_list|)
block|,
DECL|enumConstant|Unpublished
name|Unpublished
argument_list|(
literal|"Unpublished"
argument_list|)
block|,
comment|// Biblatex
DECL|enumConstant|BookInBook
name|BookInBook
argument_list|(
literal|"BookInBook"
argument_list|)
block|,
DECL|enumConstant|InReference
name|InReference
argument_list|(
literal|"InReference"
argument_list|)
block|,
DECL|enumConstant|MvBook
name|MvBook
argument_list|(
literal|"MvBook"
argument_list|)
block|,
DECL|enumConstant|MvCollection
name|MvCollection
argument_list|(
literal|"MvCollection"
argument_list|)
block|,
DECL|enumConstant|MvProceedings
name|MvProceedings
argument_list|(
literal|"MvProceedings"
argument_list|)
block|,
DECL|enumConstant|MvReference
name|MvReference
argument_list|(
literal|"MvReference"
argument_list|)
block|,
DECL|enumConstant|Online
name|Online
argument_list|(
literal|"Online"
argument_list|)
block|,
DECL|enumConstant|Reference
name|Reference
argument_list|(
literal|"Reference"
argument_list|)
block|,
DECL|enumConstant|Report
name|Report
argument_list|(
literal|"Report"
argument_list|)
block|,
DECL|enumConstant|Set
name|Set
argument_list|(
literal|"Set"
argument_list|)
block|,
DECL|enumConstant|SuppBook
name|SuppBook
argument_list|(
literal|"SuppBook"
argument_list|)
block|,
DECL|enumConstant|SuppCollection
name|SuppCollection
argument_list|(
literal|"SuppCollection"
argument_list|)
block|,
DECL|enumConstant|SuppPeriodical
name|SuppPeriodical
argument_list|(
literal|"SuppPeriodical"
argument_list|)
block|,
DECL|enumConstant|Thesis
name|Thesis
argument_list|(
literal|"Thesis"
argument_list|)
block|,
DECL|enumConstant|WWW
name|WWW
argument_list|(
literal|"WWW"
argument_list|)
block|,
DECL|enumConstant|Software
name|Software
argument_list|(
literal|"Software"
argument_list|)
block|,
DECL|enumConstant|DATESET
name|DATESET
argument_list|(
literal|"DataSet"
argument_list|)
block|;
DECL|field|displayName
specifier|private
specifier|final
name|String
name|displayName
decl_stmt|;
DECL|method|StandardEntryType (String displayName)
name|StandardEntryType
parameter_list|(
name|String
name|displayName
parameter_list|)
block|{
name|this
operator|.
name|displayName
operator|=
name|displayName
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
name|displayName
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getDisplayName ()
specifier|public
name|String
name|getDisplayName
parameter_list|()
block|{
return|return
name|displayName
return|;
block|}
block|}
end_enum

end_unit

