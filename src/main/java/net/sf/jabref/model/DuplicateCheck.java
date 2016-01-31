begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.model
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
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
name|bibtex
operator|.
name|EntryTypes
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
name|database
operator|.
name|BibDatabaseMode
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
name|AuthorList
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
name|database
operator|.
name|BibDatabase
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
name|EntryType
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_comment
comment|/**  * This class contains utility method for duplicate checking of entries.  */
end_comment

begin_class
DECL|class|DuplicateCheck
specifier|public
class|class
name|DuplicateCheck
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|DuplicateCheck
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/*      * Integer values for indicating result of duplicate check (for entries):      *      */
DECL|field|NOT_EQUAL
specifier|private
specifier|static
specifier|final
name|int
name|NOT_EQUAL
init|=
literal|0
decl_stmt|;
DECL|field|EQUAL
specifier|private
specifier|static
specifier|final
name|int
name|EQUAL
init|=
literal|1
decl_stmt|;
DECL|field|EMPTY_IN_ONE
specifier|private
specifier|static
specifier|final
name|int
name|EMPTY_IN_ONE
init|=
literal|2
decl_stmt|;
DECL|field|EMPTY_IN_TWO
specifier|private
specifier|static
specifier|final
name|int
name|EMPTY_IN_TWO
init|=
literal|3
decl_stmt|;
DECL|field|EMPTY_IN_BOTH
specifier|private
specifier|static
specifier|final
name|int
name|EMPTY_IN_BOTH
init|=
literal|4
decl_stmt|;
DECL|field|duplicateThreshold
specifier|public
specifier|static
name|double
name|duplicateThreshold
init|=
literal|0.75
decl_stmt|;
comment|// The overall threshold to signal a duplicate pair
comment|// Non-required fields are investigated only if the required fields give a value within
comment|// the doubt range of the threshold:
DECL|field|DOUBT_RANGE
specifier|private
specifier|static
specifier|final
name|double
name|DOUBT_RANGE
init|=
literal|0.05
decl_stmt|;
DECL|field|REQUIRED_WEIGHT
specifier|private
specifier|static
specifier|final
name|double
name|REQUIRED_WEIGHT
init|=
literal|3
decl_stmt|;
comment|// Weighting of all required fields
comment|// Extra weighting of those fields that are most likely to provide correct duplicate detection:
DECL|field|FIELD_WEIGHTS
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|FIELD_WEIGHTS
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
static|static
block|{
name|DuplicateCheck
operator|.
name|FIELD_WEIGHTS
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
literal|2.5
argument_list|)
expr_stmt|;
name|DuplicateCheck
operator|.
name|FIELD_WEIGHTS
operator|.
name|put
argument_list|(
literal|"editor"
argument_list|,
literal|2.5
argument_list|)
expr_stmt|;
name|DuplicateCheck
operator|.
name|FIELD_WEIGHTS
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
literal|3.
argument_list|)
expr_stmt|;
name|DuplicateCheck
operator|.
name|FIELD_WEIGHTS
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
literal|2.
argument_list|)
expr_stmt|;
block|}
comment|/**      * Checks if the two entries represent the same publication.      *      * @param one BibEntry      * @param two BibEntry      * @return boolean      */
DECL|method|isDuplicate (BibEntry one, BibEntry two, BibDatabaseMode bibDatabaseMode)
specifier|public
specifier|static
name|boolean
name|isDuplicate
parameter_list|(
name|BibEntry
name|one
parameter_list|,
name|BibEntry
name|two
parameter_list|,
name|BibDatabaseMode
name|bibDatabaseMode
parameter_list|)
block|{
comment|// First check if they are of the same type - a necessary condition:
if|if
condition|(
operator|!
name|one
operator|.
name|getType
argument_list|()
operator|.
name|equals
argument_list|(
name|two
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|EntryType
name|type
init|=
name|EntryTypes
operator|.
name|getTypeOrDefault
argument_list|(
name|one
operator|.
name|getType
argument_list|()
argument_list|,
name|bibDatabaseMode
argument_list|)
decl_stmt|;
comment|// The check if they have the same required fields:
name|java
operator|.
name|util
operator|.
name|List
argument_list|<
name|String
argument_list|>
name|var
init|=
name|type
operator|.
name|getRequiredFieldsFlat
argument_list|()
decl_stmt|;
name|String
index|[]
name|fields
init|=
name|var
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|var
operator|.
name|size
argument_list|()
index|]
argument_list|)
decl_stmt|;
name|double
index|[]
name|req
decl_stmt|;
if|if
condition|(
name|fields
operator|==
literal|null
condition|)
block|{
name|req
operator|=
operator|new
name|double
index|[]
block|{
literal|0.
block|,
literal|0.
block|}
expr_stmt|;
block|}
else|else
block|{
name|req
operator|=
name|DuplicateCheck
operator|.
name|compareFieldSet
argument_list|(
name|fields
argument_list|,
name|one
argument_list|,
name|two
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|Math
operator|.
name|abs
argument_list|(
name|req
index|[
literal|0
index|]
operator|-
name|DuplicateCheck
operator|.
name|duplicateThreshold
argument_list|)
operator|>
name|DuplicateCheck
operator|.
name|DOUBT_RANGE
condition|)
block|{
comment|// Far from the threshold value, so we base our decision on the req. fields only
return|return
name|req
index|[
literal|0
index|]
operator|>=
name|DuplicateCheck
operator|.
name|duplicateThreshold
return|;
block|}
comment|// Close to the threshold value, so we take a look at the optional fields, if any:
name|java
operator|.
name|util
operator|.
name|List
argument_list|<
name|String
argument_list|>
name|optionalFields
init|=
name|type
operator|.
name|getOptionalFields
argument_list|()
decl_stmt|;
name|fields
operator|=
name|optionalFields
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|optionalFields
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|fields
operator|!=
literal|null
condition|)
block|{
name|double
index|[]
name|opt
init|=
name|DuplicateCheck
operator|.
name|compareFieldSet
argument_list|(
name|fields
argument_list|,
name|one
argument_list|,
name|two
argument_list|)
decl_stmt|;
name|double
name|totValue
init|=
operator|(
operator|(
name|DuplicateCheck
operator|.
name|REQUIRED_WEIGHT
operator|*
name|req
index|[
literal|0
index|]
operator|*
name|req
index|[
literal|1
index|]
operator|)
operator|+
operator|(
name|opt
index|[
literal|0
index|]
operator|*
name|opt
index|[
literal|1
index|]
operator|)
operator|)
operator|/
operator|(
operator|(
name|req
index|[
literal|1
index|]
operator|*
name|DuplicateCheck
operator|.
name|REQUIRED_WEIGHT
operator|)
operator|+
name|opt
index|[
literal|1
index|]
operator|)
decl_stmt|;
return|return
name|totValue
operator|>=
name|DuplicateCheck
operator|.
name|duplicateThreshold
return|;
block|}
return|return
name|req
index|[
literal|0
index|]
operator|>=
name|DuplicateCheck
operator|.
name|duplicateThreshold
return|;
block|}
DECL|method|compareFieldSet (String[] fields, BibEntry one, BibEntry two)
specifier|private
specifier|static
name|double
index|[]
name|compareFieldSet
parameter_list|(
name|String
index|[]
name|fields
parameter_list|,
name|BibEntry
name|one
parameter_list|,
name|BibEntry
name|two
parameter_list|)
block|{
name|double
name|res
init|=
literal|0
decl_stmt|;
name|double
name|totWeights
init|=
literal|0.
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
name|double
name|weight
decl_stmt|;
if|if
condition|(
name|DuplicateCheck
operator|.
name|FIELD_WEIGHTS
operator|.
name|containsKey
argument_list|(
name|field
argument_list|)
condition|)
block|{
name|weight
operator|=
name|DuplicateCheck
operator|.
name|FIELD_WEIGHTS
operator|.
name|get
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|weight
operator|=
literal|1.0
expr_stmt|;
block|}
name|totWeights
operator|+=
name|weight
expr_stmt|;
name|int
name|result
init|=
name|DuplicateCheck
operator|.
name|compareSingleField
argument_list|(
name|field
argument_list|,
name|one
argument_list|,
name|two
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|==
name|EQUAL
condition|)
block|{
name|res
operator|+=
name|weight
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|result
operator|==
name|EMPTY_IN_BOTH
condition|)
block|{
name|totWeights
operator|-=
name|weight
expr_stmt|;
block|}
block|}
if|if
condition|(
name|totWeights
operator|>
literal|0
condition|)
block|{
return|return
operator|new
name|double
index|[]
block|{
name|res
operator|/
name|totWeights
block|,
name|totWeights
block|}
return|;
block|}
return|return
operator|new
name|double
index|[]
block|{
literal|0.5
block|,
literal|0.0
block|}
return|;
block|}
DECL|method|compareSingleField (String field, BibEntry one, BibEntry two)
specifier|private
specifier|static
name|int
name|compareSingleField
parameter_list|(
name|String
name|field
parameter_list|,
name|BibEntry
name|one
parameter_list|,
name|BibEntry
name|two
parameter_list|)
block|{
name|String
name|s1
init|=
name|one
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|String
name|s2
init|=
name|two
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
if|if
condition|(
name|s1
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|s2
operator|==
literal|null
condition|)
block|{
return|return
name|EMPTY_IN_BOTH
return|;
block|}
return|return
name|EMPTY_IN_ONE
return|;
block|}
elseif|else
if|if
condition|(
name|s2
operator|==
literal|null
condition|)
block|{
return|return
name|EMPTY_IN_TWO
return|;
block|}
if|if
condition|(
literal|"author"
operator|.
name|equals
argument_list|(
name|field
argument_list|)
operator|||
literal|"editor"
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
comment|// Specific for name fields.
comment|// Harmonise case:
name|String
name|auth1
init|=
name|AuthorList
operator|.
name|fixAuthor_lastNameOnlyCommas
argument_list|(
name|s1
argument_list|,
literal|false
argument_list|)
operator|.
name|replace
argument_list|(
literal|" and "
argument_list|,
literal|" "
argument_list|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|String
name|auth2
init|=
name|AuthorList
operator|.
name|fixAuthor_lastNameOnlyCommas
argument_list|(
name|s2
argument_list|,
literal|false
argument_list|)
operator|.
name|replace
argument_list|(
literal|" and "
argument_list|,
literal|" "
argument_list|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|double
name|similarity
init|=
name|DuplicateCheck
operator|.
name|correlateByWords
argument_list|(
name|auth1
argument_list|,
name|auth2
argument_list|)
decl_stmt|;
if|if
condition|(
name|similarity
operator|>
literal|0.8
condition|)
block|{
return|return
name|EQUAL
return|;
block|}
return|return
name|NOT_EQUAL
return|;
block|}
elseif|else
if|if
condition|(
literal|"pages"
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
comment|// Pages can be given with a variety of delimiters, "-", "--", " - ", " -- ".
comment|// We do a replace to harmonize these to a simple "-":
comment|// After this, a simple test for equality should be enough:
name|s1
operator|=
name|s1
operator|.
name|replaceAll
argument_list|(
literal|"[- ]+"
argument_list|,
literal|"-"
argument_list|)
expr_stmt|;
name|s2
operator|=
name|s2
operator|.
name|replaceAll
argument_list|(
literal|"[- ]+"
argument_list|,
literal|"-"
argument_list|)
expr_stmt|;
if|if
condition|(
name|s1
operator|.
name|equals
argument_list|(
name|s2
argument_list|)
condition|)
block|{
return|return
name|EQUAL
return|;
block|}
return|return
name|NOT_EQUAL
return|;
block|}
elseif|else
if|if
condition|(
literal|"journal"
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
comment|// We do not attempt to harmonize abbreviation state of the journal names,
comment|// but we remove periods from the names in case they are abbreviated with
comment|// and without dots:
name|s1
operator|=
name|s1
operator|.
name|replace
argument_list|(
literal|"."
argument_list|,
literal|""
argument_list|)
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|s2
operator|=
name|s2
operator|.
name|replace
argument_list|(
literal|"."
argument_list|,
literal|""
argument_list|)
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|double
name|similarity
init|=
name|DuplicateCheck
operator|.
name|correlateByWords
argument_list|(
name|s1
argument_list|,
name|s2
argument_list|)
decl_stmt|;
if|if
condition|(
name|similarity
operator|>
literal|0.8
condition|)
block|{
return|return
name|EQUAL
return|;
block|}
return|return
name|NOT_EQUAL
return|;
block|}
else|else
block|{
name|s1
operator|=
name|s1
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|s2
operator|=
name|s2
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|double
name|similarity
init|=
name|DuplicateCheck
operator|.
name|correlateByWords
argument_list|(
name|s1
argument_list|,
name|s2
argument_list|)
decl_stmt|;
if|if
condition|(
name|similarity
operator|>
literal|0.8
condition|)
block|{
return|return
name|EQUAL
return|;
block|}
return|return
name|NOT_EQUAL
return|;
block|}
block|}
DECL|method|compareEntriesStrictly (BibEntry one, BibEntry two)
specifier|public
specifier|static
name|double
name|compareEntriesStrictly
parameter_list|(
name|BibEntry
name|one
parameter_list|,
name|BibEntry
name|two
parameter_list|)
block|{
name|HashSet
argument_list|<
name|String
argument_list|>
name|allFields
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
name|allFields
operator|.
name|addAll
argument_list|(
name|one
operator|.
name|getFieldNames
argument_list|()
argument_list|)
expr_stmt|;
name|allFields
operator|.
name|addAll
argument_list|(
name|two
operator|.
name|getFieldNames
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|score
init|=
literal|0
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|allFields
control|)
block|{
name|Object
name|en
init|=
name|one
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|Object
name|to
init|=
name|two
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
operator|(
name|en
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|to
operator|!=
literal|null
operator|)
operator|&&
name|en
operator|.
name|equals
argument_list|(
name|to
argument_list|)
operator|)
operator|||
operator|(
operator|(
name|en
operator|==
literal|null
operator|)
operator|&&
operator|(
name|to
operator|==
literal|null
operator|)
operator|)
condition|)
block|{
name|score
operator|++
expr_stmt|;
block|}
block|}
if|if
condition|(
name|score
operator|==
name|allFields
operator|.
name|size
argument_list|()
condition|)
block|{
return|return
literal|1.01
return|;
comment|// Just to make sure we can
comment|// use score>1 without
comment|// trouble.
block|}
return|return
operator|(
name|double
operator|)
name|score
operator|/
name|allFields
operator|.
name|size
argument_list|()
return|;
block|}
comment|/**      * Goes through all entries in the given database, and if at least one of      * them is a duplicate of the given entry, as per      * Util.isDuplicate(BibEntry, BibEntry), the duplicate is returned.      * The search is terminated when the first duplicate is found.      *      * @param database The database to search.      * @param entry    The entry of which we are looking for duplicates.      * @return The first duplicate entry found. null if no duplicates are found.      */
DECL|method|containsDuplicate (BibDatabase database, BibEntry entry, BibDatabaseMode bibDatabaseMode)
specifier|public
specifier|static
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|containsDuplicate
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|BibDatabaseMode
name|bibDatabaseMode
parameter_list|)
block|{
for|for
control|(
name|BibEntry
name|other
range|:
name|database
operator|.
name|getEntries
argument_list|()
control|)
block|{
if|if
condition|(
name|DuplicateCheck
operator|.
name|isDuplicate
argument_list|(
name|entry
argument_list|,
name|other
argument_list|,
name|bibDatabaseMode
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|other
argument_list|)
return|;
comment|// Duplicate found.
block|}
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
comment|// No duplicate found.
block|}
comment|/**      * Compare two strings on the basis of word-by-word correlation analysis.      *      * @param s1       The first string      * @param s2       The second string      * @return a value in the interval [0, 1] indicating the degree of match.      */
DECL|method|correlateByWords (String s1, String s2)
specifier|public
specifier|static
name|double
name|correlateByWords
parameter_list|(
name|String
name|s1
parameter_list|,
name|String
name|s2
parameter_list|)
block|{
name|String
index|[]
name|w1
init|=
name|s1
operator|.
name|split
argument_list|(
literal|"\\s"
argument_list|)
decl_stmt|;
name|String
index|[]
name|w2
init|=
name|s2
operator|.
name|split
argument_list|(
literal|"\\s"
argument_list|)
decl_stmt|;
name|int
name|n
init|=
name|Math
operator|.
name|min
argument_list|(
name|w1
operator|.
name|length
argument_list|,
name|w2
operator|.
name|length
argument_list|)
decl_stmt|;
name|int
name|misses
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|n
condition|;
name|i
operator|++
control|)
block|{
name|double
name|corr
init|=
name|similarity
argument_list|(
name|w1
index|[
name|i
index|]
argument_list|,
name|w2
index|[
name|i
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|corr
operator|<
literal|0.75
condition|)
block|{
name|misses
operator|++
expr_stmt|;
block|}
block|}
name|double
name|missRate
init|=
operator|(
name|double
operator|)
name|misses
operator|/
operator|(
name|double
operator|)
name|n
decl_stmt|;
return|return
literal|1
operator|-
name|missRate
return|;
block|}
comment|/**      * Calculates the similarity (a number within 0 and 1) between two strings.      * http://stackoverflow.com/questions/955110/similarity-string-comparison-in-java      */
DECL|method|similarity (String s1, String s2)
specifier|private
specifier|static
name|double
name|similarity
parameter_list|(
name|String
name|s1
parameter_list|,
name|String
name|s2
parameter_list|)
block|{
name|String
name|longer
init|=
name|s1
decl_stmt|,
name|shorter
init|=
name|s2
decl_stmt|;
if|if
condition|(
name|s1
operator|.
name|length
argument_list|()
operator|<
name|s2
operator|.
name|length
argument_list|()
condition|)
block|{
comment|// longer should always have greater length
name|longer
operator|=
name|s2
expr_stmt|;
name|shorter
operator|=
name|s1
expr_stmt|;
block|}
name|int
name|longerLength
init|=
name|longer
operator|.
name|length
argument_list|()
decl_stmt|;
if|if
condition|(
name|longerLength
operator|==
literal|0
condition|)
block|{
return|return
literal|1.0
return|;
comment|/* both strings are zero length */
block|}
name|double
name|sim
init|=
operator|(
name|longerLength
operator|-
name|editDistance
argument_list|(
name|longer
argument_list|,
name|shorter
argument_list|)
operator|)
operator|/
operator|(
name|double
operator|)
name|longerLength
decl_stmt|;
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Longer string: "
operator|+
name|longer
operator|+
literal|" Shorter string: "
operator|+
name|shorter
operator|+
literal|" Similarity: "
operator|+
name|sim
argument_list|)
expr_stmt|;
return|return
name|sim
return|;
block|}
comment|/*     * Levenshtein Edit Distance     * http://stackoverflow.com/questions/955110/similarity-string-comparison-in-java     */
DECL|method|editDistance (String s1, String s2)
specifier|private
specifier|static
name|int
name|editDistance
parameter_list|(
name|String
name|s1
parameter_list|,
name|String
name|s2
parameter_list|)
block|{
name|s1
operator|=
name|s1
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|s2
operator|=
name|s2
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|int
index|[]
name|costs
init|=
operator|new
name|int
index|[
name|s2
operator|.
name|length
argument_list|()
operator|+
literal|1
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<=
name|s1
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|int
name|lastValue
init|=
name|i
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<=
name|s2
operator|.
name|length
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|==
literal|0
condition|)
block|{
name|costs
index|[
name|j
index|]
operator|=
name|j
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|j
operator|>
literal|0
condition|)
block|{
name|int
name|newValue
init|=
name|costs
index|[
name|j
operator|-
literal|1
index|]
decl_stmt|;
if|if
condition|(
name|s1
operator|.
name|charAt
argument_list|(
name|i
operator|-
literal|1
argument_list|)
operator|!=
name|s2
operator|.
name|charAt
argument_list|(
name|j
operator|-
literal|1
argument_list|)
condition|)
block|{
name|newValue
operator|=
name|Math
operator|.
name|min
argument_list|(
name|Math
operator|.
name|min
argument_list|(
name|newValue
argument_list|,
name|lastValue
argument_list|)
argument_list|,
name|costs
index|[
name|j
index|]
argument_list|)
operator|+
literal|1
expr_stmt|;
block|}
name|costs
index|[
name|j
operator|-
literal|1
index|]
operator|=
name|lastValue
expr_stmt|;
name|lastValue
operator|=
name|newValue
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|i
operator|>
literal|0
condition|)
block|{
name|costs
index|[
name|s2
operator|.
name|length
argument_list|()
index|]
operator|=
name|lastValue
expr_stmt|;
block|}
block|}
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"String 1: "
operator|+
name|s1
operator|+
literal|" String 2: "
operator|+
name|s2
operator|+
literal|" Distance: "
operator|+
name|costs
index|[
name|s2
operator|.
name|length
argument_list|()
index|]
argument_list|)
expr_stmt|;
return|return
name|costs
index|[
name|s2
operator|.
name|length
argument_list|()
index|]
return|;
block|}
block|}
end_class

end_unit

