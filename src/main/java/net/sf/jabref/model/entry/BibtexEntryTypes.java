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

begin_class
DECL|class|BibtexEntryTypes
specifier|public
class|class
name|BibtexEntryTypes
block|{
comment|// Get an entry type defined in BibtexEntryType
DECL|method|getEntryType (String type)
specifier|public
specifier|static
name|BibtexEntryType
name|getEntryType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
comment|// decide which entryType object to return
name|Object
name|o
init|=
name|EntryTypes
operator|.
name|getType
argument_list|(
name|type
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|BibtexEntryType
operator|)
name|o
return|;
block|}
else|else
block|{
return|return
name|OTHER
return|;
block|}
block|}
DECL|field|OTHER
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|OTHER
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Other"
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
DECL|field|ARTICLE
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|ARTICLE
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllOptional
argument_list|(
literal|"volume"
argument_list|,
literal|"pages"
argument_list|,
literal|"number"
argument_list|,
literal|"month"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
comment|//- "volume", "pages", "part", "eid"
name|addAllRequired
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"journal"
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
comment|//+ "volume", "pages"
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Article"
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
literal|"author"
block|,
literal|"title"
block|,
literal|"journal"
block|,
literal|"year"
block|,
literal|"bibtexkey"
block|,
literal|"volume"
block|,
literal|"pages"
block|}
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|BOOKLET
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|BOOKLET
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
literal|"howpublished"
argument_list|,
literal|"address"
argument_list|,
literal|"month"
argument_list|,
literal|"year"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
comment|//+ "lastchecked"
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Booklet"
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
literal|"bibtexkey"
block|}
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|INBOOK
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|INBOOK
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
literal|"author/editor"
block|,
literal|"title"
block|,
literal|"chapter/pages"
block|,
literal|"year"
block|,
literal|"publisher"
block|}
argument_list|)
argument_list|)
decl_stmt|;
block|{
name|addAllOptional
argument_list|(
literal|"volume"
argument_list|,
literal|"number"
argument_list|,
literal|"series"
argument_list|,
literal|"type"
argument_list|,
literal|"address"
argument_list|,
literal|"edition"
argument_list|,
literal|"month"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
comment|//+ "pages"
name|addAllRequired
argument_list|(
literal|"chapter"
argument_list|,
literal|"pages"
argument_list|,
literal|"title"
argument_list|,
literal|"publisher"
argument_list|,
literal|"year"
argument_list|,
literal|"editor"
argument_list|,
literal|"author"
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
literal|"InBook"
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
literal|"title"
block|,
literal|"publisher"
block|,
literal|"year"
block|,
literal|"bibtexkey"
block|}
argument_list|,
name|database
argument_list|)
operator|&&
name|entry
operator|.
name|atLeastOnePresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"editor"
block|}
argument_list|,
name|database
argument_list|)
operator|&&
name|entry
operator|.
name|atLeastOnePresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"chapter"
block|,
literal|"pages"
block|}
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|BOOK
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|BOOK
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
literal|"publisher"
block|,
literal|"year"
block|,
literal|"author/editor"
block|}
argument_list|)
argument_list|)
decl_stmt|;
block|{
name|addAllRequired
argument_list|(
literal|"title"
argument_list|,
literal|"publisher"
argument_list|,
literal|"year"
argument_list|,
literal|"editor"
argument_list|,
literal|"author"
argument_list|)
expr_stmt|;
name|addAllOptional
argument_list|(
literal|"volume"
argument_list|,
literal|"number"
argument_list|,
literal|"series"
argument_list|,
literal|"address"
argument_list|,
literal|"edition"
argument_list|,
literal|"month"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
comment|//+ pages
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Book"
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
literal|"title"
block|,
literal|"publisher"
block|,
literal|"year"
block|,
literal|"bibtexkey"
block|}
argument_list|,
name|database
argument_list|)
operator|&&
name|entry
operator|.
name|atLeastOnePresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"editor"
block|}
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|INCOLLECTION
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|INCOLLECTION
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllRequired
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"booktitle"
argument_list|,
literal|"publisher"
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
name|addAllOptional
argument_list|(
literal|"editor"
argument_list|,
literal|"volume"
argument_list|,
literal|"number"
argument_list|,
literal|"series"
argument_list|,
literal|"type"
argument_list|,
literal|"chapter"
argument_list|,
literal|"pages"
argument_list|,
literal|"address"
argument_list|,
literal|"edition"
argument_list|,
literal|"month"
argument_list|,
literal|"note"
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
literal|"InCollection"
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
literal|"author"
block|,
literal|"title"
block|,
literal|"booktitle"
block|,
literal|"publisher"
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
DECL|field|CONFERENCE
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|CONFERENCE
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
literal|"volume"
argument_list|,
literal|"number"
argument_list|,
literal|"series"
argument_list|,
literal|"pages"
argument_list|,
literal|"address"
argument_list|,
literal|"month"
argument_list|,
literal|"organization"
argument_list|,
literal|"publisher"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
name|addAllRequired
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"booktitle"
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
literal|"Conference"
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
literal|"author"
block|,
literal|"title"
block|,
literal|"booktitle"
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
DECL|field|INPROCEEDINGS
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|INPROCEEDINGS
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
literal|"volume"
argument_list|,
literal|"number"
argument_list|,
literal|"series"
argument_list|,
literal|"pages"
argument_list|,
literal|"address"
argument_list|,
literal|"month"
argument_list|,
literal|"organization"
argument_list|,
literal|"publisher"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
name|addAllRequired
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"booktitle"
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
literal|"InProceedings"
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
literal|"author"
block|,
literal|"title"
block|,
literal|"booktitle"
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
DECL|field|PROCEEDINGS
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|PROCEEDINGS
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
literal|"volume"
argument_list|,
literal|"number"
argument_list|,
literal|"series"
argument_list|,
literal|"address"
argument_list|,
literal|"publisher"
argument_list|,
literal|"note"
argument_list|,
literal|"month"
argument_list|,
literal|"organization"
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
literal|"Proceedings"
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
DECL|field|MANUAL
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|MANUAL
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
literal|"organization"
argument_list|,
literal|"address"
argument_list|,
literal|"edition"
argument_list|,
literal|"month"
argument_list|,
literal|"year"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
name|addAllRequired
argument_list|(
literal|"title"
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
literal|"Manual"
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
literal|"bibtexkey"
block|}
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|TECHREPORT
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|TECHREPORT
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllOptional
argument_list|(
literal|"type"
argument_list|,
literal|"number"
argument_list|,
literal|"address"
argument_list|,
literal|"month"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
name|addAllRequired
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"institution"
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
literal|"TechReport"
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
literal|"author"
block|,
literal|"title"
block|,
literal|"institution"
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
DECL|field|MASTERSTHESIS
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|MASTERSTHESIS
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllOptional
argument_list|(
literal|"type"
argument_list|,
literal|"address"
argument_list|,
literal|"month"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
name|addAllRequired
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"school"
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
literal|"MastersThesis"
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
literal|"author"
block|,
literal|"title"
block|,
literal|"school"
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
DECL|field|PHDTHESIS
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|PHDTHESIS
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllOptional
argument_list|(
literal|"type"
argument_list|,
literal|"address"
argument_list|,
literal|"month"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
name|addAllRequired
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"school"
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
literal|"PhdThesis"
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
literal|"author"
block|,
literal|"title"
block|,
literal|"school"
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
DECL|field|UNPUBLISHED
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|UNPUBLISHED
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllOptional
argument_list|(
literal|"month"
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
name|addAllRequired
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"note"
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
literal|"Unpublished"
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
literal|"author"
block|,
literal|"title"
block|,
literal|"note"
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
DECL|field|PERIODICAL
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
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
DECL|field|PATENT
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
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
literal|"number"
block|,
literal|"bibtexkey"
block|}
argument_list|,
name|database
argument_list|)
operator|&&
name|entry
operator|.
name|atLeastOnePresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"year"
block|,
literal|"yearfiled"
block|}
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|STANDARD
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
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
literal|"title"
block|,
literal|"bibtexkey"
block|}
argument_list|,
name|database
argument_list|)
operator|&&
name|entry
operator|.
name|atLeastOnePresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"organization"
block|,
literal|"institution"
block|}
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|ELECTRONIC
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
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
DECL|field|MISC
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|MISC
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
literal|"howpublished"
argument_list|,
literal|"month"
argument_list|,
literal|"year"
argument_list|,
literal|"note"
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
literal|"Misc"
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
comment|/**      * This type is used for IEEEtran.bst to control various      * be repeated or not. Not a very elegant solution, but it works...      */
DECL|field|IEEETRANBSTCTL
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
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
annotation|@
name|Override
specifier|public
name|boolean
name|isVisibleAtNewEntryDialog
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
block|}
decl_stmt|;
comment|/**      * This type is provided as an emergency choice if the user makes      * customization changes that remove the type of an entry.      */
DECL|field|TYPELESS
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|TYPELESS
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Typeless"
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
literal|false
return|;
block|}
block|}
decl_stmt|;
block|}
end_class

end_unit

