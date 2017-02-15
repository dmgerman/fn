begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.msbib
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|msbib
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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
name|BibEntry
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
name|FieldName
import|;
end_import

begin_class
DECL|class|MSBibConverter
specifier|public
class|class
name|MSBibConverter
block|{
DECL|field|MSBIB_PREFIX
specifier|private
specifier|static
specifier|final
name|String
name|MSBIB_PREFIX
init|=
literal|"msbib-"
decl_stmt|;
DECL|field|BIBTEX_PREFIX
specifier|private
specifier|static
specifier|final
name|String
name|BIBTEX_PREFIX
init|=
literal|"BIBTEX_"
decl_stmt|;
DECL|method|convert (BibEntry entry)
specifier|public
specifier|static
name|MSBibEntry
name|convert
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|MSBibEntry
name|result
init|=
operator|new
name|MSBibEntry
argument_list|()
decl_stmt|;
comment|// memorize original type
name|result
operator|.
name|fields
operator|.
name|put
argument_list|(
name|BIBTEX_PREFIX
operator|+
literal|"Entry"
argument_list|,
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
comment|// define new type
name|String
name|msbibType
init|=
name|result
operator|.
name|fields
operator|.
name|put
argument_list|(
literal|"SourceType"
argument_list|,
name|MSBibMapping
operator|.
name|getMSBibEntryType
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
operator|.
name|name
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|entry
operator|.
name|getFieldNames
argument_list|()
control|)
block|{
comment|// clean field
name|String
name|unicodeField
init|=
name|entry
operator|.
name|getLatexFreeField
argument_list|(
name|field
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
decl_stmt|;
if|if
condition|(
name|MSBibMapping
operator|.
name|getMSBibField
argument_list|(
name|field
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|fields
operator|.
name|put
argument_list|(
name|MSBibMapping
operator|.
name|getMSBibField
argument_list|(
name|field
argument_list|)
argument_list|,
name|unicodeField
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Duplicate: also added as BookTitle
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|BOOKTITLE
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|booktitle
lambda|->
name|result
operator|.
name|conferenceName
operator|=
name|booktitle
argument_list|)
expr_stmt|;
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|PAGES
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|pages
lambda|->
name|result
operator|.
name|pages
operator|=
operator|new
name|PageNumbers
argument_list|(
name|pages
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|getField
argument_list|(
name|MSBIB_PREFIX
operator|+
literal|"accessed"
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|accesed
lambda|->
name|result
operator|.
name|dateAccessed
operator|=
name|accesed
argument_list|)
expr_stmt|;
comment|// TODO: currently this can never happen
if|if
condition|(
literal|"SoundRecording"
operator|.
name|equals
argument_list|(
name|msbibType
argument_list|)
condition|)
block|{
name|result
operator|.
name|albumTitle
operator|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
comment|// TODO: currently this can never happen
if|if
condition|(
literal|"Interview"
operator|.
name|equals
argument_list|(
name|msbibType
argument_list|)
condition|)
block|{
name|result
operator|.
name|broadcastTitle
operator|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|result
operator|.
name|number
operator|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|NUMBER
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"Patent"
operator|.
name|equalsIgnoreCase
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|result
operator|.
name|patentNumber
operator|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|NUMBER
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|result
operator|.
name|number
operator|=
literal|null
expr_stmt|;
block|}
name|result
operator|.
name|journalName
operator|=
name|entry
operator|.
name|getFieldOrAlias
argument_list|(
name|FieldName
operator|.
name|JOURNAL
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|result
operator|.
name|month
operator|=
name|entry
operator|.
name|getFieldOrAlias
argument_list|(
name|FieldName
operator|.
name|MONTH
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|)
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|result
operator|.
name|year
operator|=
name|entry
operator|.
name|getFieldOrAlias
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
comment|// Value must be converted
comment|//Currently only english is supported
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|LANGUAGE
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|lang
lambda|->
name|result
operator|.
name|fields
operator|.
name|put
argument_list|(
literal|"LCID"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|MSBibMapping
operator|.
name|getLCID
argument_list|(
name|lang
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|StringBuilder
name|sbNumber
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|ISBN
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|isbn
lambda|->
name|sbNumber
operator|.
name|append
argument_list|(
literal|" ISBN: "
operator|+
name|isbn
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|ISSN
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|issn
lambda|->
name|sbNumber
operator|.
name|append
argument_list|(
literal|" ISSN: "
operator|+
name|issn
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|getField
argument_list|(
literal|"lccn"
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|lccn
lambda|->
name|sbNumber
operator|.
name|append
argument_list|(
literal|"LCCN: "
operator|+
name|lccn
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|getField
argument_list|(
literal|"mrnumber"
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|mrnumber
lambda|->
name|sbNumber
operator|.
name|append
argument_list|(
literal|" MRN: "
operator|+
name|mrnumber
argument_list|)
argument_list|)
expr_stmt|;
name|result
operator|.
name|standardNumber
operator|=
name|sbNumber
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|result
operator|.
name|standardNumber
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|result
operator|.
name|standardNumber
operator|=
literal|null
expr_stmt|;
block|}
name|result
operator|.
name|address
operator|=
name|entry
operator|.
name|getFieldOrAlias
argument_list|(
name|FieldName
operator|.
name|ADDRESS
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|TYPE
argument_list|)
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|result
operator|.
name|thesisType
operator|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|TYPE
argument_list|)
operator|.
name|get
argument_list|()
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
literal|"techreport"
operator|.
name|equalsIgnoreCase
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|result
operator|.
name|thesisType
operator|=
literal|"Tech. rep."
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"mastersthesis"
operator|.
name|equalsIgnoreCase
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|result
operator|.
name|thesisType
operator|=
literal|"Master's thesis"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"phdthesis"
operator|.
name|equalsIgnoreCase
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|result
operator|.
name|thesisType
operator|=
literal|"Ph.D. dissertation"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"unpublished"
operator|.
name|equalsIgnoreCase
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|result
operator|.
name|thesisType
operator|=
literal|"unpublished"
expr_stmt|;
block|}
block|}
comment|// TODO: currently this can never happen
if|if
condition|(
operator|(
literal|"InternetSite"
operator|.
name|equals
argument_list|(
name|msbibType
argument_list|)
operator|||
literal|"DocumentFromInternetSite"
operator|.
name|equals
argument_list|(
name|msbibType
argument_list|)
operator|)
condition|)
block|{
name|result
operator|.
name|internetSiteTitle
operator|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
comment|// TODO: currently only Misc can happen
if|if
condition|(
literal|"ElectronicSource"
operator|.
name|equals
argument_list|(
name|msbibType
argument_list|)
operator|||
literal|"Art"
operator|.
name|equals
argument_list|(
name|msbibType
argument_list|)
operator|||
literal|"Misc"
operator|.
name|equals
argument_list|(
name|msbibType
argument_list|)
condition|)
block|{
name|result
operator|.
name|publicationTitle
operator|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|entry
operator|.
name|getLatexFreeField
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|authors
lambda|->
name|result
operator|.
name|authors
operator|=
name|getAuthors
argument_list|(
name|authors
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|getLatexFreeField
argument_list|(
name|FieldName
operator|.
name|EDITOR
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|editors
lambda|->
name|result
operator|.
name|editors
operator|=
name|getAuthors
argument_list|(
name|editors
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
DECL|method|getAuthors (String authors)
specifier|private
specifier|static
name|List
argument_list|<
name|PersonName
argument_list|>
name|getAuthors
parameter_list|(
name|String
name|authors
parameter_list|)
block|{
name|List
argument_list|<
name|PersonName
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|authors
operator|.
name|toUpperCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
operator|.
name|contains
argument_list|(
literal|" AND "
argument_list|)
condition|)
block|{
name|String
index|[]
name|names
init|=
name|authors
operator|.
name|split
argument_list|(
literal|" (?i)and "
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|name
range|:
name|names
control|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|PersonName
argument_list|(
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|PersonName
argument_list|(
name|authors
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

