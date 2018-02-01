begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLConnection
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
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

begin_import
import|import
name|org
operator|.
name|json
operator|.
name|JSONArray
import|;
end_import

begin_import
import|import
name|org
operator|.
name|json
operator|.
name|JSONObject
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Represents the Application Version with the major and minor number, the full Version String and if it's a developer  * version  */
end_comment

begin_class
DECL|class|Version
specifier|public
class|class
name|Version
block|{
DECL|field|JABREF_DOWNLOAD_URL
specifier|public
specifier|static
specifier|final
name|String
name|JABREF_DOWNLOAD_URL
init|=
literal|"https://downloads.jabref.org"
decl_stmt|;
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|Version
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|UNKNOWN_VERSION
specifier|private
specifier|static
specifier|final
name|Version
name|UNKNOWN_VERSION
init|=
operator|new
name|Version
argument_list|()
decl_stmt|;
DECL|field|VERSION_PATTERN
specifier|private
specifier|final
specifier|static
name|Pattern
name|VERSION_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(?<major>\\d+)(\\.(?<minor>\\d+))?(\\.(?<patch>\\d+))?(?<stage>-alpha|-beta)?(?<dev>-?dev)?.*"
argument_list|)
decl_stmt|;
DECL|field|JABREF_GITHUB_RELEASES
specifier|private
specifier|static
specifier|final
name|String
name|JABREF_GITHUB_RELEASES
init|=
literal|"https://api.github.com/repos/JabRef/JabRef/releases"
decl_stmt|;
DECL|field|fullVersion
specifier|private
name|String
name|fullVersion
init|=
name|BuildInfo
operator|.
name|UNKNOWN_VERSION
decl_stmt|;
DECL|field|major
specifier|private
name|int
name|major
init|=
operator|-
literal|1
decl_stmt|;
DECL|field|minor
specifier|private
name|int
name|minor
init|=
operator|-
literal|1
decl_stmt|;
DECL|field|patch
specifier|private
name|int
name|patch
init|=
operator|-
literal|1
decl_stmt|;
DECL|field|developmentStage
specifier|private
name|DevelopmentStage
name|developmentStage
init|=
name|DevelopmentStage
operator|.
name|UNKNOWN
decl_stmt|;
DECL|field|isDevelopmentVersion
specifier|private
name|boolean
name|isDevelopmentVersion
decl_stmt|;
comment|/**      * Dummy constructor to create a local object (and  {@link Version#UNKNOWN_VERSION})      */
DECL|method|Version ()
specifier|private
name|Version
parameter_list|()
block|{     }
comment|/**      * @param version must be in form of following pattern: {@code (\d+)(\.(\d+))?(\.(\d+))?(-alpha|-beta)?(-?dev)?}      *                (e.g., 3.3; 3.4-dev)      * @return the parsed version or {@link Version#UNKNOWN_VERSION} if an error occurred      */
DECL|method|parse (String version)
specifier|public
specifier|static
name|Version
name|parse
parameter_list|(
name|String
name|version
parameter_list|)
block|{
if|if
condition|(
operator|(
name|version
operator|==
literal|null
operator|)
operator|||
literal|""
operator|.
name|equals
argument_list|(
name|version
argument_list|)
operator|||
name|version
operator|.
name|equals
argument_list|(
name|BuildInfo
operator|.
name|UNKNOWN_VERSION
argument_list|)
operator|||
literal|"${version}"
operator|.
name|equals
argument_list|(
name|version
argument_list|)
condition|)
block|{
return|return
name|UNKNOWN_VERSION
return|;
block|}
name|Version
name|parsedVersion
init|=
operator|new
name|Version
argument_list|()
decl_stmt|;
name|parsedVersion
operator|.
name|fullVersion
operator|=
name|version
expr_stmt|;
name|Matcher
name|matcher
init|=
name|VERSION_PATTERN
operator|.
name|matcher
argument_list|(
name|version
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
try|try
block|{
name|parsedVersion
operator|.
name|major
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|matcher
operator|.
name|group
argument_list|(
literal|"major"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|minorString
init|=
name|matcher
operator|.
name|group
argument_list|(
literal|"minor"
argument_list|)
decl_stmt|;
name|parsedVersion
operator|.
name|minor
operator|=
name|minorString
operator|==
literal|null
condition|?
literal|0
else|:
name|Integer
operator|.
name|parseInt
argument_list|(
name|minorString
argument_list|)
expr_stmt|;
name|String
name|patchString
init|=
name|matcher
operator|.
name|group
argument_list|(
literal|"patch"
argument_list|)
decl_stmt|;
name|parsedVersion
operator|.
name|patch
operator|=
name|patchString
operator|==
literal|null
condition|?
literal|0
else|:
name|Integer
operator|.
name|parseInt
argument_list|(
name|patchString
argument_list|)
expr_stmt|;
name|String
name|versionStageString
init|=
name|matcher
operator|.
name|group
argument_list|(
literal|"stage"
argument_list|)
decl_stmt|;
name|parsedVersion
operator|.
name|developmentStage
operator|=
name|versionStageString
operator|==
literal|null
condition|?
name|DevelopmentStage
operator|.
name|STABLE
else|:
name|DevelopmentStage
operator|.
name|parse
argument_list|(
name|versionStageString
argument_list|)
expr_stmt|;
name|parsedVersion
operator|.
name|isDevelopmentVersion
operator|=
name|matcher
operator|.
name|group
argument_list|(
literal|"dev"
argument_list|)
operator|!=
literal|null
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Invalid version string used: "
operator|+
name|version
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|UNKNOWN_VERSION
return|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Invalid version pattern is used"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|UNKNOWN_VERSION
return|;
block|}
block|}
else|else
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Version could not be recognized by the pattern"
argument_list|)
expr_stmt|;
return|return
name|UNKNOWN_VERSION
return|;
block|}
return|return
name|parsedVersion
return|;
block|}
comment|/**      * Grabs all the available releases from the GitHub repository      */
DECL|method|getAllAvailableVersions ()
specifier|public
specifier|static
name|List
argument_list|<
name|Version
argument_list|>
name|getAllAvailableVersions
parameter_list|()
throws|throws
name|IOException
block|{
name|URLConnection
name|connection
init|=
operator|new
name|URL
argument_list|(
name|JABREF_GITHUB_RELEASES
argument_list|)
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|connection
operator|.
name|setRequestProperty
argument_list|(
literal|"Accept-Charset"
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
try|try
init|(
name|BufferedReader
name|rd
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|connection
operator|.
name|getInputStream
argument_list|()
argument_list|)
argument_list|)
init|)
block|{
name|List
argument_list|<
name|Version
argument_list|>
name|versions
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|JSONArray
name|objects
init|=
operator|new
name|JSONArray
argument_list|(
name|rd
operator|.
name|readLine
argument_list|()
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
name|objects
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|JSONObject
name|jsonObject
init|=
name|objects
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|Version
name|version
init|=
name|Version
operator|.
name|parse
argument_list|(
name|jsonObject
operator|.
name|getString
argument_list|(
literal|"tag_name"
argument_list|)
operator|.
name|replaceFirst
argument_list|(
literal|"v"
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|versions
operator|.
name|add
argument_list|(
name|version
argument_list|)
expr_stmt|;
block|}
return|return
name|versions
return|;
block|}
block|}
comment|/**      * @return true if this version is newer than the passed one      */
DECL|method|isNewerThan (Version otherVersion)
specifier|public
name|boolean
name|isNewerThan
parameter_list|(
name|Version
name|otherVersion
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|otherVersion
argument_list|)
expr_stmt|;
if|if
condition|(
name|Objects
operator|.
name|equals
argument_list|(
name|this
argument_list|,
name|otherVersion
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
elseif|else
if|if
condition|(
name|this
operator|.
name|getFullVersion
argument_list|()
operator|.
name|equals
argument_list|(
name|BuildInfo
operator|.
name|UNKNOWN_VERSION
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
elseif|else
if|if
condition|(
name|otherVersion
operator|.
name|getFullVersion
argument_list|()
operator|.
name|equals
argument_list|(
name|BuildInfo
operator|.
name|UNKNOWN_VERSION
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|// compare the majors
if|if
condition|(
name|this
operator|.
name|getMajor
argument_list|()
operator|>
name|otherVersion
operator|.
name|getMajor
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
elseif|else
if|if
condition|(
name|this
operator|.
name|getMajor
argument_list|()
operator|==
name|otherVersion
operator|.
name|getMajor
argument_list|()
condition|)
block|{
comment|// if the majors are equal compare the minors
if|if
condition|(
name|this
operator|.
name|getMinor
argument_list|()
operator|>
name|otherVersion
operator|.
name|getMinor
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
elseif|else
if|if
condition|(
name|this
operator|.
name|getMinor
argument_list|()
operator|==
name|otherVersion
operator|.
name|getMinor
argument_list|()
condition|)
block|{
comment|// if the minors are equal compare the patch numbers
if|if
condition|(
name|this
operator|.
name|getPatch
argument_list|()
operator|>
name|otherVersion
operator|.
name|getPatch
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
elseif|else
if|if
condition|(
name|this
operator|.
name|getPatch
argument_list|()
operator|==
name|otherVersion
operator|.
name|getPatch
argument_list|()
condition|)
block|{
comment|// if the patch numbers are equal compare the development stages
if|if
condition|(
name|this
operator|.
name|developmentStage
operator|.
name|isMoreStableThan
argument_list|(
name|otherVersion
operator|.
name|developmentStage
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
elseif|else
if|if
condition|(
name|this
operator|.
name|developmentStage
operator|==
name|otherVersion
operator|.
name|developmentStage
condition|)
block|{
comment|// if the stage is equal check if this version is in development and the other is not
return|return
operator|!
name|this
operator|.
name|isDevelopmentVersion
operator|&&
name|otherVersion
operator|.
name|isDevelopmentVersion
return|;
block|}
block|}
block|}
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Checks if this version should be updated to one of the given ones.      * Ignoring the other Version if this one is Stable and the other one is not.      *      * @return The version this one should be updated to, or an empty Optional      */
DECL|method|shouldBeUpdatedTo (List<Version> availableVersions)
specifier|public
name|Optional
argument_list|<
name|Version
argument_list|>
name|shouldBeUpdatedTo
parameter_list|(
name|List
argument_list|<
name|Version
argument_list|>
name|availableVersions
parameter_list|)
block|{
name|Optional
argument_list|<
name|Version
argument_list|>
name|newerVersion
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
for|for
control|(
name|Version
name|version
range|:
name|availableVersions
control|)
block|{
if|if
condition|(
name|this
operator|.
name|shouldBeUpdatedTo
argument_list|(
name|version
argument_list|)
operator|&&
operator|(
operator|!
name|newerVersion
operator|.
name|isPresent
argument_list|()
operator|||
name|version
operator|.
name|isNewerThan
argument_list|(
name|newerVersion
operator|.
name|get
argument_list|()
argument_list|)
operator|)
condition|)
block|{
name|newerVersion
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|version
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|newerVersion
return|;
block|}
comment|/**      * Checks if this version should be updated to the given one.      * Ignoring the other Version if this one is Stable and the other one is not.      *      * @return True if this version should be updated to the given one      */
DECL|method|shouldBeUpdatedTo (Version otherVersion)
specifier|public
name|boolean
name|shouldBeUpdatedTo
parameter_list|(
name|Version
name|otherVersion
parameter_list|)
block|{
comment|// ignoring the other version if it is not stable, except if this version itself is not stable
if|if
condition|(
name|developmentStage
operator|==
name|Version
operator|.
name|DevelopmentStage
operator|.
name|STABLE
operator|&&
name|otherVersion
operator|.
name|developmentStage
operator|!=
name|Version
operator|.
name|DevelopmentStage
operator|.
name|STABLE
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|// check if the other version is newer than given one
return|return
name|otherVersion
operator|.
name|isNewerThan
argument_list|(
name|this
argument_list|)
return|;
block|}
DECL|method|getFullVersion ()
specifier|public
name|String
name|getFullVersion
parameter_list|()
block|{
return|return
name|fullVersion
return|;
block|}
DECL|method|getMajor ()
specifier|public
name|int
name|getMajor
parameter_list|()
block|{
return|return
name|major
return|;
block|}
DECL|method|getMinor ()
specifier|public
name|int
name|getMinor
parameter_list|()
block|{
return|return
name|minor
return|;
block|}
DECL|method|getPatch ()
specifier|public
name|int
name|getPatch
parameter_list|()
block|{
return|return
name|patch
return|;
block|}
DECL|method|isDevelopmentVersion ()
specifier|public
name|boolean
name|isDevelopmentVersion
parameter_list|()
block|{
return|return
name|isDevelopmentVersion
return|;
block|}
comment|/**      * @return The link to the changelog on GitHub to this specific version (https://github.com/JabRef/jabref/blob/vX.X/CHANGELOG.md)      */
DECL|method|getChangelogUrl ()
specifier|public
name|String
name|getChangelogUrl
parameter_list|()
block|{
if|if
condition|(
name|isDevelopmentVersion
condition|)
block|{
return|return
literal|"https://github.com/JabRef/jabref/blob/master/CHANGELOG.md#unreleased"
return|;
block|}
else|else
block|{
name|StringBuilder
name|changelogLink
init|=
operator|new
name|StringBuilder
argument_list|()
operator|.
name|append
argument_list|(
literal|"https://github.com/JabRef/jabref/blob/v"
argument_list|)
operator|.
name|append
argument_list|(
name|this
operator|.
name|getMajor
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"."
argument_list|)
operator|.
name|append
argument_list|(
name|this
operator|.
name|getMinor
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|getPatch
argument_list|()
operator|!=
literal|0
condition|)
block|{
name|changelogLink
operator|.
name|append
argument_list|(
literal|"."
argument_list|)
operator|.
name|append
argument_list|(
name|this
operator|.
name|getPatch
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|changelogLink
operator|.
name|append
argument_list|(
name|this
operator|.
name|developmentStage
operator|.
name|stage
argument_list|)
operator|.
name|append
argument_list|(
literal|"/CHANGELOG.md"
argument_list|)
expr_stmt|;
return|return
name|changelogLink
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|equals (Object other)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|other
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|other
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
operator|!
operator|(
name|other
operator|instanceof
name|Version
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|// till all the information are stripped from the fullversion this should suffice
return|return
name|this
operator|.
name|getFullVersion
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|Version
operator|)
name|other
operator|)
operator|.
name|getFullVersion
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getFullVersion
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|this
operator|.
name|getFullVersion
argument_list|()
return|;
block|}
DECL|enum|DevelopmentStage
specifier|public
enum|enum
name|DevelopmentStage
block|{
DECL|enumConstant|UNKNOWN
name|UNKNOWN
argument_list|(
literal|""
argument_list|,
literal|0
argument_list|)
block|,
DECL|enumConstant|ALPHA
name|ALPHA
argument_list|(
literal|"-alpha"
argument_list|,
literal|1
argument_list|)
block|,
DECL|enumConstant|BETA
name|BETA
argument_list|(
literal|"-beta"
argument_list|,
literal|2
argument_list|)
block|,
DECL|enumConstant|STABLE
name|STABLE
argument_list|(
literal|""
argument_list|,
literal|3
argument_list|)
block|;
comment|/**          * describes how stable this stage is, the higher the better          */
DECL|field|stability
specifier|private
specifier|final
name|int
name|stability
decl_stmt|;
DECL|field|stage
specifier|private
specifier|final
name|String
name|stage
decl_stmt|;
DECL|method|DevelopmentStage (String stage, int stability)
name|DevelopmentStage
parameter_list|(
name|String
name|stage
parameter_list|,
name|int
name|stability
parameter_list|)
block|{
name|this
operator|.
name|stage
operator|=
name|stage
expr_stmt|;
name|this
operator|.
name|stability
operator|=
name|stability
expr_stmt|;
block|}
DECL|method|parse (String stage)
specifier|public
specifier|static
name|DevelopmentStage
name|parse
parameter_list|(
name|String
name|stage
parameter_list|)
block|{
if|if
condition|(
name|stage
operator|==
literal|null
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"The stage cannot be null"
argument_list|)
expr_stmt|;
return|return
name|UNKNOWN
return|;
block|}
elseif|else
if|if
condition|(
name|stage
operator|.
name|equals
argument_list|(
name|STABLE
operator|.
name|stage
argument_list|)
condition|)
block|{
return|return
name|STABLE
return|;
block|}
elseif|else
if|if
condition|(
name|stage
operator|.
name|equals
argument_list|(
name|ALPHA
operator|.
name|stage
argument_list|)
condition|)
block|{
return|return
name|ALPHA
return|;
block|}
elseif|else
if|if
condition|(
name|stage
operator|.
name|equals
argument_list|(
name|BETA
operator|.
name|stage
argument_list|)
condition|)
block|{
return|return
name|BETA
return|;
block|}
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Unknown development stage: "
operator|+
name|stage
argument_list|)
expr_stmt|;
return|return
name|UNKNOWN
return|;
block|}
comment|/**          * @return true if this stage is more stable than the {@code otherStage}          */
DECL|method|isMoreStableThan (DevelopmentStage otherStage)
specifier|public
name|boolean
name|isMoreStableThan
parameter_list|(
name|DevelopmentStage
name|otherStage
parameter_list|)
block|{
return|return
name|this
operator|.
name|stability
operator|>
name|otherStage
operator|.
name|stability
return|;
block|}
block|}
block|}
end_class

end_unit

