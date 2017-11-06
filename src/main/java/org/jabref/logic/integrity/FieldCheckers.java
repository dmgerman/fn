begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.integrity
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|integrity
package|;
end_package

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
name|stream
operator|.
name|Collectors
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
name|journals
operator|.
name|JournalAbbreviationRepository
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabaseContext
import|;
end_import

begin_import
import|import
name|org
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

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|InternalBibtexFields
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|metadata
operator|.
name|FileDirectoryPreferences
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|collect
operator|.
name|ArrayListMultimap
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|collect
operator|.
name|Multimap
import|;
end_import

begin_class
DECL|class|FieldCheckers
specifier|public
class|class
name|FieldCheckers
block|{
DECL|field|fieldChecker
specifier|private
name|Multimap
argument_list|<
name|String
argument_list|,
name|ValueChecker
argument_list|>
name|fieldChecker
decl_stmt|;
DECL|method|FieldCheckers (BibDatabaseContext databaseContext, FileDirectoryPreferences fileDirectoryPreferences, JournalAbbreviationRepository abbreviationRepository)
specifier|public
name|FieldCheckers
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|FileDirectoryPreferences
name|fileDirectoryPreferences
parameter_list|,
name|JournalAbbreviationRepository
name|abbreviationRepository
parameter_list|)
block|{
name|fieldChecker
operator|=
name|getAllMap
argument_list|(
name|databaseContext
argument_list|,
name|fileDirectoryPreferences
argument_list|,
name|abbreviationRepository
argument_list|)
expr_stmt|;
block|}
DECL|method|getAllMap (BibDatabaseContext databaseContext, FileDirectoryPreferences fileDirectoryPreferences, JournalAbbreviationRepository abbreviationRepository)
specifier|private
specifier|static
name|Multimap
argument_list|<
name|String
argument_list|,
name|ValueChecker
argument_list|>
name|getAllMap
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|FileDirectoryPreferences
name|fileDirectoryPreferences
parameter_list|,
name|JournalAbbreviationRepository
name|abbreviationRepository
parameter_list|)
block|{
name|ArrayListMultimap
argument_list|<
name|String
argument_list|,
name|ValueChecker
argument_list|>
name|fieldCheckers
init|=
name|ArrayListMultimap
operator|.
name|create
argument_list|(
literal|50
argument_list|,
literal|10
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|InternalBibtexFields
operator|.
name|getJournalNameFields
argument_list|()
control|)
block|{
name|fieldCheckers
operator|.
name|put
argument_list|(
name|field
argument_list|,
operator|new
name|AbbreviationChecker
argument_list|(
name|abbreviationRepository
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|String
name|field
range|:
name|InternalBibtexFields
operator|.
name|getBookNameFields
argument_list|()
control|)
block|{
name|fieldCheckers
operator|.
name|put
argument_list|(
name|field
argument_list|,
operator|new
name|AbbreviationChecker
argument_list|(
name|abbreviationRepository
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|String
name|field
range|:
name|InternalBibtexFields
operator|.
name|getPersonNameFields
argument_list|()
control|)
block|{
name|fieldCheckers
operator|.
name|put
argument_list|(
name|field
argument_list|,
operator|new
name|PersonNamesChecker
argument_list|(
name|databaseContext
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|fieldCheckers
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|BOOKTITLE
argument_list|,
operator|new
name|BooktitleChecker
argument_list|()
argument_list|)
expr_stmt|;
name|fieldCheckers
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
operator|new
name|BracketChecker
argument_list|()
argument_list|)
expr_stmt|;
name|fieldCheckers
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
operator|new
name|TitleChecker
argument_list|(
name|databaseContext
argument_list|)
argument_list|)
expr_stmt|;
name|fieldCheckers
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|DOI
argument_list|,
operator|new
name|DOIValidityChecker
argument_list|()
argument_list|)
expr_stmt|;
name|fieldCheckers
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|EDITION
argument_list|,
operator|new
name|EditionChecker
argument_list|(
name|databaseContext
argument_list|)
argument_list|)
expr_stmt|;
name|fieldCheckers
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|,
operator|new
name|FileChecker
argument_list|(
name|databaseContext
argument_list|,
name|fileDirectoryPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|fieldCheckers
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|HOWPUBLISHED
argument_list|,
operator|new
name|HowPublishedChecker
argument_list|(
name|databaseContext
argument_list|)
argument_list|)
expr_stmt|;
name|fieldCheckers
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|ISBN
argument_list|,
operator|new
name|ISBNChecker
argument_list|()
argument_list|)
expr_stmt|;
name|fieldCheckers
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|ISSN
argument_list|,
operator|new
name|ISSNChecker
argument_list|()
argument_list|)
expr_stmt|;
name|fieldCheckers
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|MONTH
argument_list|,
operator|new
name|MonthChecker
argument_list|(
name|databaseContext
argument_list|)
argument_list|)
expr_stmt|;
name|fieldCheckers
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|NOTE
argument_list|,
operator|new
name|NoteChecker
argument_list|(
name|databaseContext
argument_list|)
argument_list|)
expr_stmt|;
name|fieldCheckers
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|PAGES
argument_list|,
operator|new
name|PagesChecker
argument_list|(
name|databaseContext
argument_list|)
argument_list|)
expr_stmt|;
name|fieldCheckers
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|,
operator|new
name|UrlChecker
argument_list|()
argument_list|)
expr_stmt|;
name|fieldCheckers
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|,
operator|new
name|YearChecker
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|fieldCheckers
return|;
block|}
DECL|method|getAll ()
specifier|public
name|List
argument_list|<
name|FieldChecker
argument_list|>
name|getAll
parameter_list|()
block|{
return|return
name|fieldChecker
operator|.
name|entries
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|pair
lambda|->
operator|new
name|FieldChecker
argument_list|(
name|pair
operator|.
name|getKey
argument_list|()
argument_list|,
name|pair
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getForField (String field)
specifier|public
name|Collection
argument_list|<
name|ValueChecker
argument_list|>
name|getForField
parameter_list|(
name|String
name|field
parameter_list|)
block|{
return|return
name|fieldChecker
operator|.
name|get
argument_list|(
name|field
argument_list|)
return|;
block|}
block|}
end_class

end_unit

