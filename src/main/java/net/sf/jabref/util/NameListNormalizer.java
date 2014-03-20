begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
package|;
end_package

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

begin_comment
comment|/**  * Class containing method(s) for normalizing author lists to BibTeX format.  */
end_comment

begin_class
DECL|class|NameListNormalizer
specifier|public
class|class
name|NameListNormalizer
block|{
DECL|field|lastFF
specifier|static
name|Pattern
name|lastFF
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(\\p{javaUpperCase}[\\p{javaLowerCase}]+) (\\p{javaUpperCase}+)"
argument_list|)
decl_stmt|;
DECL|field|lastFdotF
specifier|static
name|Pattern
name|lastFdotF
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(\\p{javaUpperCase}[\\p{javaLowerCase}]+) ([\\. \\p{javaUpperCase}]+)"
argument_list|)
decl_stmt|;
DECL|field|FFlast
specifier|static
name|Pattern
name|FFlast
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(\\p{javaUpperCase}+) (\\p{javaUpperCase}[\\p{javaLowerCase}]+)"
argument_list|)
decl_stmt|;
DECL|field|FdotFlast
specifier|static
name|Pattern
name|FdotFlast
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"([\\. \\p{javaUpperCase}]+) (\\p{javaUpperCase}[\\p{javaLowerCase}]+)"
argument_list|)
decl_stmt|;
DECL|field|singleName
specifier|static
name|Pattern
name|singleName
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(\\p{javaUpperCase}[\\p{javaLowerCase}]*)"
argument_list|)
decl_stmt|;
comment|/*public static void main(String[] args) {         normalizeAuthorList("Staci D. Bilbo and Smith SH and Jaclyn M Schwarz");         //System.out.println(normalizeAuthorList("Ãlver MA"));         //System.out.println(normalizeAuthorList("Ãlver MA, GG Ãie, Ãie GG, Alfredsen JÃÃ, Jo Alfredsen, Olsen Y.Y. and Olsen Y. Y."));         //System.out.println(normalizeAuthorList("Ãlver MA, GG Ãie, Ãie GG, Alfredsen JÃÃ, Jo Alfredsen, Olsen Y.Y., Olsen Y. Y."));         //System.out.println(normalizeAuthorList("Alver, Morten and Alver, Morten O and Alfredsen, JA and Olsen, Y.Y."));         //System.out.println(normalizeAuthorList("Alver, MA; Alfredsen, JA; Olsen Y.Y."));     }*/
DECL|method|normalizeAuthorList (String in)
specifier|public
specifier|static
name|String
name|normalizeAuthorList
parameter_list|(
name|String
name|in
parameter_list|)
block|{
name|boolean
name|andSep
init|=
literal|false
decl_stmt|,
name|semicolonSep
init|=
literal|false
decl_stmt|,
name|commaSep
init|=
literal|false
decl_stmt|;
name|String
name|author
decl_stmt|;
name|String
index|[]
name|authors
init|=
name|in
operator|.
name|split
argument_list|(
literal|"( |,)and "
argument_list|,
operator|-
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|authors
operator|.
name|length
operator|>
literal|1
condition|)
name|andSep
operator|=
literal|true
expr_stmt|;
else|else
block|{
comment|/*             If there are no "and" separators in the original string, we assume it either means that             the author list is comma or semicolon separated or that it contains only a single name.             If there is a semicolon, we go by that. If not, we assume commas, and count the parts             separated by commas to determine which it is.             */
name|String
index|[]
name|a2
init|=
name|in
operator|.
name|split
argument_list|(
literal|"; "
argument_list|)
decl_stmt|;
if|if
condition|(
name|a2
operator|.
name|length
operator|>
literal|1
condition|)
block|{
name|semicolonSep
operator|=
literal|true
expr_stmt|;
name|authors
operator|=
name|a2
expr_stmt|;
block|}
else|else
block|{
name|a2
operator|=
name|in
operator|.
name|split
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
if|if
condition|(
name|a2
operator|.
name|length
operator|>
literal|3
condition|)
block|{
comment|// Probably more than a single author, so we split by commas.
name|commaSep
operator|=
literal|true
expr_stmt|;
name|authors
operator|=
name|a2
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|a2
operator|.
name|length
operator|==
literal|3
condition|)
block|{
comment|// This could be a BibTeX formatted name containing a Jr particle,
comment|// e.g. Smith, Jr., Peter
comment|// We check if the middle part is<= 3 characters. If not, we assume we are
comment|// dealing with three authors.
if|if
condition|(
name|a2
index|[
literal|1
index|]
operator|.
name|length
argument_list|()
operator|>
literal|3
condition|)
name|authors
operator|=
name|a2
expr_stmt|;
block|}
block|}
block|}
block|}
comment|// Remove leading and trailing whitespaces from each name:
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|authors
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|authors
index|[
name|i
index|]
operator|=
name|authors
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
comment|// If we found an and separator, there could possibly be semicolon or
comment|// comma separation before the last separator. If there are two or more
comment|// and separators, we can dismiss this possibility.
comment|// If there is only a single and separator, check closer:
if|if
condition|(
name|andSep
operator|&&
operator|(
name|authors
operator|.
name|length
operator|==
literal|2
operator|)
condition|)
block|{
comment|// Check if the first part is semicolon separated:
name|String
index|[]
name|semiSep
init|=
name|authors
index|[
literal|0
index|]
operator|.
name|split
argument_list|(
literal|"; "
argument_list|)
decl_stmt|;
if|if
condition|(
name|semiSep
operator|.
name|length
operator|>
literal|1
condition|)
block|{
comment|// Ok, it looks like this is the case. Use separation by semicolons:
name|String
index|[]
name|newAuthors
init|=
operator|new
name|String
index|[
literal|1
operator|+
name|semiSep
operator|.
name|length
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
operator|<
name|semiSep
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|newAuthors
index|[
name|i
index|]
operator|=
name|semiSep
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
name|newAuthors
index|[
name|semiSep
operator|.
name|length
index|]
operator|=
name|authors
index|[
literal|1
index|]
expr_stmt|;
name|authors
operator|=
name|newAuthors
expr_stmt|;
block|}
else|else
block|{
comment|// Check if there is a comma in the last name. If so, we can assume that comma
comment|// is not used to separate the names:
name|boolean
name|lnfn
init|=
operator|(
name|authors
index|[
literal|1
index|]
operator|.
name|indexOf
argument_list|(
literal|","
argument_list|)
operator|>
literal|0
operator|)
decl_stmt|;
if|if
condition|(
operator|!
name|lnfn
condition|)
block|{
name|String
index|[]
name|cmSep
init|=
name|authors
index|[
literal|0
index|]
operator|.
name|split
argument_list|(
literal|", "
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmSep
operator|.
name|length
operator|>
literal|1
condition|)
block|{
comment|// This means that the last name doesn't contain a comma, but the first
comment|// one contains one or more. This indicates that the names leading up to
comment|// the single "and" are comma separated:
name|String
index|[]
name|newAuthors
init|=
operator|new
name|String
index|[
literal|1
operator|+
name|cmSep
operator|.
name|length
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
operator|<
name|cmSep
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|newAuthors
index|[
name|i
index|]
operator|=
name|cmSep
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
name|newAuthors
index|[
name|cmSep
operator|.
name|length
index|]
operator|=
name|authors
index|[
literal|1
index|]
expr_stmt|;
name|authors
operator|=
name|newAuthors
expr_stmt|;
block|}
block|}
block|}
block|}
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
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
name|authors
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|norm
init|=
name|normalizeName
argument_list|(
name|authors
index|[
name|i
index|]
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|norm
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|authors
operator|.
name|length
operator|-
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|normalizeName (String name)
specifier|public
specifier|static
name|String
name|normalizeName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|Matcher
name|m
init|=
name|lastFF
operator|.
name|matcher
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|.
name|matches
argument_list|()
condition|)
block|{
name|String
name|initials
init|=
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|initials
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|initials
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'.'
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|initials
operator|.
name|length
argument_list|()
operator|-
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
name|m
operator|=
name|lastFdotF
operator|.
name|matcher
argument_list|(
name|name
argument_list|)
expr_stmt|;
if|if
condition|(
name|m
operator|.
name|matches
argument_list|()
condition|)
block|{
name|String
name|initials
init|=
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"[\\. ]+"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|initials
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|initials
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'.'
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|initials
operator|.
name|length
argument_list|()
operator|-
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
name|m
operator|=
name|FFlast
operator|.
name|matcher
argument_list|(
name|name
argument_list|)
expr_stmt|;
if|if
condition|(
name|m
operator|.
name|matches
argument_list|()
condition|)
block|{
name|String
name|initials
init|=
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|initials
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|initials
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'.'
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|initials
operator|.
name|length
argument_list|()
operator|-
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
name|m
operator|=
name|FdotFlast
operator|.
name|matcher
argument_list|(
name|name
argument_list|)
expr_stmt|;
if|if
condition|(
name|m
operator|.
name|matches
argument_list|()
condition|)
block|{
name|String
name|initials
init|=
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"[\\. ]+"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|initials
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|initials
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'.'
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|initials
operator|.
name|length
argument_list|()
operator|-
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
if|if
condition|(
name|name
operator|.
name|indexOf
argument_list|(
literal|','
argument_list|)
operator|>=
literal|0
condition|)
block|{
comment|// Name contains comma
name|int
name|index
init|=
name|name
operator|.
name|lastIndexOf
argument_list|(
literal|','
argument_list|)
decl_stmt|;
comment|// If the comma is at the end of the name, just remove it to prevent index error:
if|if
condition|(
name|index
operator|==
name|name
operator|.
name|length
argument_list|()
operator|-
literal|1
condition|)
name|name
operator|=
name|name
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|name
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|name
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
comment|// Check if the remainder is a single name:
name|String
name|fName
init|=
name|name
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
index|[]
name|fParts
init|=
name|fName
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
if|if
condition|(
name|fParts
operator|.
name|length
operator|>
literal|1
condition|)
block|{
comment|// Multiple parts. Add all of them, and add a dot if they are single letter parts:
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fParts
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|fParts
index|[
name|i
index|]
operator|.
name|length
argument_list|()
operator|==
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
name|fParts
index|[
name|i
index|]
argument_list|)
operator|.
name|append
argument_list|(
literal|"."
argument_list|)
expr_stmt|;
else|else
name|sb
operator|.
name|append
argument_list|(
name|fParts
index|[
name|i
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|fParts
operator|.
name|length
operator|-
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// Only a single part. Check if it looks like a name or initials:
name|Matcher
name|m2
init|=
name|singleName
operator|.
name|matcher
argument_list|(
name|fParts
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|m2
operator|.
name|matches
argument_list|()
condition|)
name|sb
operator|.
name|append
argument_list|(
name|fParts
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
else|else
block|{
comment|// It looks like initials.
name|String
name|initials
init|=
name|fParts
index|[
literal|0
index|]
operator|.
name|replaceAll
argument_list|(
literal|"[\\.]+"
argument_list|,
literal|""
argument_list|)
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
name|initials
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|initials
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'.'
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|initials
operator|.
name|length
argument_list|()
operator|-
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
else|else
block|{
comment|// Name doesn't contain comma
name|String
index|[]
name|parts
init|=
name|name
operator|.
name|split
argument_list|(
literal|" +"
argument_list|)
decl_stmt|;
name|boolean
name|allNames
init|=
literal|true
decl_stmt|;
for|for
control|(
name|String
name|part
range|:
name|parts
control|)
block|{
name|m
operator|=
name|singleName
operator|.
name|matcher
argument_list|(
name|part
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|m
operator|.
name|matches
argument_list|()
condition|)
block|{
name|allNames
operator|=
literal|false
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|allNames
condition|)
block|{
comment|// Looks like a name written in full with first name first.
comment|// Change into last name first format:
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|parts
index|[
name|parts
operator|.
name|length
operator|-
literal|1
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|parts
operator|.
name|length
operator|>
literal|1
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|","
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|parts
operator|.
name|length
operator|-
literal|1
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|parts
index|[
name|i
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|parts
index|[
name|i
index|]
operator|.
name|length
argument_list|()
operator|==
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"."
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
return|return
name|name
return|;
block|}
block|}
end_class

end_unit

