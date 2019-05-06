begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.bibtexextractor
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|bibtexextractor
package|;
end_package

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
name|BibEntry
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
name|BiblatexEntryTypes
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
name|Calendar
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_class
DECL|class|BibtexExtractor
specifier|public
class|class
name|BibtexExtractor
block|{
DECL|field|INITIALS_GROUP
specifier|private
specifier|final
specifier|static
name|String
name|INITIALS_GROUP
init|=
literal|"INITIALS"
decl_stmt|;
DECL|field|LASTNAME_GROUP
specifier|private
specifier|final
specifier|static
name|String
name|LASTNAME_GROUP
init|=
literal|"LASTNAME"
decl_stmt|;
DECL|field|urls
specifier|private
name|ArrayList
argument_list|<
name|String
argument_list|>
name|urls
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|authors
specifier|private
name|ArrayList
argument_list|<
name|String
argument_list|>
name|authors
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|year
specifier|private
name|String
name|year
init|=
operator|new
name|String
argument_list|()
decl_stmt|;
DECL|field|urlPattern
specifier|private
specifier|static
specifier|final
name|Pattern
name|urlPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
operator|+
literal|"(([\\w\\-]+\\.)+?([\\w\\-.~]+\\/?)*"
operator|+
literal|"[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)"
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
operator||
name|Pattern
operator|.
name|MULTILINE
operator||
name|Pattern
operator|.
name|DOTALL
argument_list|)
decl_stmt|;
DECL|field|yearPattern
specifier|private
specifier|static
specifier|final
name|Pattern
name|yearPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\d{4}"
argument_list|)
decl_stmt|;
DECL|field|authorPattern1
specifier|private
specifier|static
specifier|final
name|Pattern
name|authorPattern1
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(?<"
operator|+
name|LASTNAME_GROUP
operator|+
literal|">\\p{Lu}\\w+),?\\s(?<"
operator|+
name|INITIALS_GROUP
operator|+
literal|">(\\p{Lu}\\.\\s){1,2})"
operator|+
literal|"\\s*(and|,|\\.)*"
argument_list|)
decl_stmt|;
DECL|field|authorPattern2
specifier|private
specifier|static
specifier|final
name|Pattern
name|authorPattern2
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(?<"
operator|+
name|INITIALS_GROUP
operator|+
literal|">(\\p{Lu}\\.\\s){1,2})(?<"
operator|+
name|LASTNAME_GROUP
operator|+
literal|">\\p{Lu}\\w+)"
operator|+
literal|"\\s*(and|,|\\.)*"
argument_list|)
decl_stmt|;
DECL|method|extract (String input)
specifier|public
name|BibEntry
name|extract
parameter_list|(
name|String
name|input
parameter_list|)
block|{
name|String
name|inputWithoutUrls
init|=
name|findUrls
argument_list|(
name|input
argument_list|)
decl_stmt|;
name|String
name|inputWithoutAuthors
init|=
name|findAuthors
argument_list|(
name|inputWithoutUrls
argument_list|)
decl_stmt|;
name|String
name|inputWithoutYear
init|=
name|findYear
argument_list|(
name|inputWithoutAuthors
argument_list|)
decl_stmt|;
return|return
name|GenerateEntity
argument_list|(
name|inputWithoutYear
argument_list|)
return|;
block|}
DECL|method|GenerateEntity (String input)
specifier|private
name|BibEntry
name|GenerateEntity
parameter_list|(
name|String
name|input
parameter_list|)
block|{
name|BibEntry
name|extractedEntity
init|=
operator|new
name|BibEntry
argument_list|(
name|BiblatexEntryTypes
operator|.
name|ARTICLE
argument_list|)
decl_stmt|;
name|extractedEntity
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|,
name|String
operator|.
name|join
argument_list|(
literal|" and "
argument_list|,
name|authors
argument_list|)
argument_list|)
expr_stmt|;
name|extractedEntity
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|COMMENT
argument_list|,
name|input
argument_list|)
expr_stmt|;
name|extractedEntity
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|,
name|String
operator|.
name|join
argument_list|(
literal|", "
argument_list|,
name|urls
argument_list|)
argument_list|)
expr_stmt|;
name|extractedEntity
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|,
name|year
argument_list|)
expr_stmt|;
return|return
name|extractedEntity
return|;
block|}
DECL|method|findUrls (String input)
specifier|private
name|String
name|findUrls
parameter_list|(
name|String
name|input
parameter_list|)
block|{
name|Matcher
name|matcher
init|=
name|urlPattern
operator|.
name|matcher
argument_list|(
name|input
argument_list|)
decl_stmt|;
while|while
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|urls
operator|.
name|add
argument_list|(
name|input
operator|.
name|substring
argument_list|(
name|matcher
operator|.
name|start
argument_list|(
literal|1
argument_list|)
argument_list|,
name|matcher
operator|.
name|end
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|fixSpaces
argument_list|(
name|matcher
operator|.
name|replaceAll
argument_list|(
literal|"[url_tag]"
argument_list|)
argument_list|)
return|;
block|}
DECL|method|findYear (String input)
specifier|private
name|String
name|findYear
parameter_list|(
name|String
name|input
parameter_list|)
block|{
name|Matcher
name|matcher
init|=
name|yearPattern
operator|.
name|matcher
argument_list|(
name|input
argument_list|)
decl_stmt|;
while|while
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|yearCandidate
init|=
name|input
operator|.
name|substring
argument_list|(
name|matcher
operator|.
name|start
argument_list|()
argument_list|,
name|matcher
operator|.
name|end
argument_list|()
argument_list|)
decl_stmt|;
name|Integer
name|intYearCandidate
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|yearCandidate
argument_list|)
decl_stmt|;
if|if
condition|(
name|intYearCandidate
operator|>
literal|1700
operator|&&
name|intYearCandidate
operator|<=
name|Calendar
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|YEAR
argument_list|)
condition|)
block|{
name|year
operator|=
name|yearCandidate
expr_stmt|;
return|return
name|fixSpaces
argument_list|(
name|input
operator|.
name|replace
argument_list|(
name|year
argument_list|,
literal|"[year_tag]"
argument_list|)
argument_list|)
return|;
block|}
block|}
return|return
name|input
return|;
block|}
DECL|method|findAuthors (String input)
specifier|private
name|String
name|findAuthors
parameter_list|(
name|String
name|input
parameter_list|)
block|{
name|String
name|currentInput
init|=
name|findAuthorsByPattern
argument_list|(
name|input
argument_list|,
name|authorPattern1
argument_list|)
decl_stmt|;
return|return
name|findAuthorsByPattern
argument_list|(
name|currentInput
argument_list|,
name|authorPattern2
argument_list|)
return|;
block|}
DECL|method|findAuthorsByPattern (String input, Pattern pattern)
specifier|private
name|String
name|findAuthorsByPattern
parameter_list|(
name|String
name|input
parameter_list|,
name|Pattern
name|pattern
parameter_list|)
block|{
name|Matcher
name|matcher
init|=
name|pattern
operator|.
name|matcher
argument_list|(
name|input
argument_list|)
decl_stmt|;
while|while
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|authors
operator|.
name|add
argument_list|(
name|GenerateAuthor
argument_list|(
name|matcher
operator|.
name|group
argument_list|(
name|LASTNAME_GROUP
argument_list|)
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
name|INITIALS_GROUP
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|fixSpaces
argument_list|(
name|matcher
operator|.
name|replaceAll
argument_list|(
literal|"[author_tag]"
argument_list|)
argument_list|)
return|;
block|}
DECL|method|GenerateAuthor (String lastName, String initials)
specifier|private
name|String
name|GenerateAuthor
parameter_list|(
name|String
name|lastName
parameter_list|,
name|String
name|initials
parameter_list|)
block|{
return|return
name|lastName
operator|+
literal|", "
operator|+
name|initials
return|;
block|}
DECL|method|fixSpaces (String input)
specifier|private
name|String
name|fixSpaces
parameter_list|(
name|String
name|input
parameter_list|)
block|{
return|return
name|input
operator|.
name|replaceAll
argument_list|(
literal|"[,.!?;:]"
argument_list|,
literal|"$0 "
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\p{Lt}"
argument_list|,
literal|" $0"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\s+"
argument_list|,
literal|" "
argument_list|)
operator|.
name|trim
argument_list|()
return|;
block|}
block|}
end_class

end_unit

