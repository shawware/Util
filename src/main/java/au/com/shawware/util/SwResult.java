/*
 * Copyright (C) 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A general result type for returning a set of values. A result can indicate success or failure.
 * For failure, an error code and message should be included.
 * A pre-defined set of non-zero codes [1..99] are reserved for generic errors.
 * For success, the error code is zero and the message is optional.
 * Sub-classes may include additional attributes or error codes (other than those reserved).
 */
public class SwResult
{
    // A pre-defined set of error codes.
    /** Indicates success. */
    public static final int CODE_OK = 0;
    /** A general, unexpected, unrecoverable error. */
    public static final int CODE_ERROR_UNEXPECTED    = 1;
    /** A general, expected, unrecoverable error. */
    public static final int CODE_ERROR_UNRECOVERABLE = 2;
    /** Indicates the attempted operation corresponding to this result is incomplete and should be retried. */
    public static final int CODE_ERROR_INCOMPLETE    = 3;
    /** Indicates the attempted operation corresponding to this result is not supported. */
    public static final int CODE_ERROR_UNSUPPORTED   = 4;
    /** Indicates the attempted operation was supplied invalid inputs (and can be retried with valid inputs). */
    public static final int CODE_ERROR_INVALID_INPUT = 5;

    /** A standard / default result indicating success. */
    public static final SwResult SUCCESS = new SwResult(true, CODE_OK, null);
    /** A standard / default result indicating an incomplete result. */
    public static final SwResult INCOMPLETE = new SwResult(false, CODE_ERROR_INCOMPLETE, null);

    /** Whether this result indicates success or failure. */
    private final boolean mSuccessful;
    /** An associated code, eg. an error code. */
    private final int mCode;
    /** An associated message, eg. an error message. */
    private final String mMessage;

    /**
     * Constructs and initialises a new instance.
     *
     * @param successful whether this result indicates success or failure
     * @param code the corresponding code
     * @param message the corresponding message
     */
    public SwResult(boolean successful, int code, String message)
    {
        mSuccessful = successful;
        mCode       = code;
        mMessage    = message;
    }

    /**
     * Factory method for failure cases.
     *
     * @param code the failure code
     * @param message the failure message
     *
     * @return The new (unsuccessful) result.
     */
    public static SwResult failedWith(int code, String message)
    {
        return new SwResult(false, code, message);
    }

    /**
     * Factory method for unsupported operations.
     *
     * @param message the corresponding message (should probably describe the operation)
     *
     * @return The new (unsupported) result.
     */
    public static SwResult unsupportedOperation(String message)
    {
        return new SwResult(false, CODE_ERROR_UNSUPPORTED, message);
    }

    /**
     * Takes the given result and standardises its code if: (a) the result
     * is unsuccessful; and (b) its code is not one of the standard error codes.
     *
     * @param result the result to standardise
     * @param unrecoverable whether the standardised code should be unrecoverable or unexpected
     *
     * @return The standardised result
     */
    public static SwResult standardiseCode(SwResult result, boolean unrecoverable)
    {
        if (result.isUnsuccessful() && !result.hasStandardCode())
        {
            result = SwResult.failedWith(
                    unrecoverable ? CODE_ERROR_UNRECOVERABLE : CODE_ERROR_UNEXPECTED,
                    result.getMessage()
            );
        }
        return result;
    }

    /**
     * @return Whether this result indicates success.
     */
    public boolean isSuccessful()
    {
        return mSuccessful;
    }

    /**
     * @return Whether this result indicates failure.
     */
    @JsonIgnore
    public boolean isUnsuccessful()
    {
        return !mSuccessful;
    }

    /**
     * @return Whether this result indicates an unsupported operation.
     */
    @JsonIgnore
    public boolean isUnsupported()
    {
        return (mCode == CODE_ERROR_UNSUPPORTED);
    }

    /**
     * @return Whether this result indicates an incomplete operation.
     */
    @JsonIgnore
    public boolean isIncomplete()
    {
        return (mCode == CODE_ERROR_INCOMPLETE);
    }

    /**
     * Whether this result's code is one of the pre-defined standard codes.
     *
     * @return Whether this result's code is one of the pre-defined standard codes.
     */
    public boolean hasStandardCode()
    {
        return ((mCode >= CODE_OK) && (mCode <= CODE_ERROR_INVALID_INPUT));
    }

    /**
     * @return This result's code.
     */
    public int getCode()
    {
        return mCode;
    }

    /**
     * @return This result's message.
     */
    public String getMessage()
    {
        return mMessage;
    }

    /**
     * @return The "un-wrapped" attributes ready for inclusion in toString().
     */
    protected final String partialToString()
    {
        return mSuccessful +
               StringUtil.COMMA_SEP +
               mCode +
               StringUtil.COMMA_SEP +
               mMessage;
    }

    @Override
    public String toString()
    {
        return StringUtil.toString(partialToString());
    }
}
