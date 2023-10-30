package com.studyolle.account.domain

import com.studyolle.account.domain.command.AccountCommand
import com.studyolle.account.domain.entity.Account
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountStore: AccountStore,
    private val passwordEncoder: PasswordEncoder,
    private val emailService: MailService
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 회원가입
     */
    fun processNewAccount(signUpForm: AccountCommand.SignUpForm) {
        /* 신규 회원 저장 */
        val newAccount = saveNewAccount(signUpForm)

        /* 이메일 토큰 발송 */
        sendSignUpConfirmEmail(newAccount)
    }

    /**
     * 이메일 토큰 발송
     * 실제로 발송은 하지 않고 DB 상태 업데이트만 한다.
     */
    private fun sendSignUpConfirmEmail(account: Account) {
        var message = "이메일 토큰 발송입니다."
        message += "userID : " + account.id
        message += "token : " + account.emailCheckToken

        /* send */
        emailService.sendEmail(message)
    }

    private fun saveNewAccount(signUpForm: AccountCommand.SignUpForm): Account {
        /* setting password */
        signUpForm.password = passwordEncoder.encode(signUpForm.password)

        /* account form to entity */
        val account: Account = signUpForm.toEntity()

        /* create email token */
        account.generateEmailCheckToken()

        /* save new account */
        accountStore.saveNewAccount(account)

        return account
    }
}