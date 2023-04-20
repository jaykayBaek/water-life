$(function(){
    let isValidatedLoginId = false;
    let isValidatedPassword = false;
    let isValidatedEmail = false;
    let isValidatedName = false;
    let isValidatedBirthYear = false;
    let isValidatedPhoneNo = false;

    $("#loginId").blur(function () {
        const loginId = $('#loginId').val();

        $('#loginId__result').html('');

        const regLoginId = /^[a-z0-9\-_]{5,20}/g;

        if(loginId.trim() == ''){
            $('#loginId__result').html('아이디를 입력하세요.');
            isValidatedLoginId = false;
            return false;
        }

        if(loginId.match(regLoginId)) {
            $.ajax({
                method: "post",
                url: "http://localhost:8080/members/register/check/login-id",
                data: {
                    loginId : loginId
                },
                async: false,
                success: function (result){
                    console.log(result);
                    isValidatedLoginId = true;
                },
                error: function (result){
                    const response = result.responseJSON;
                    console.log(response);
                    $('#loginId__result').html(response.message);
                    isValidatedLoginId = false;
                }
            })
        } else{
            $('#loginId__result').html('5~20자의 영문 소문자, 숫자와 특수기호 -와 _만 사용 가능합니다.');
            isValidatedLoginId = false;
        }

        isValidatedLoginId = true;
    });

    $('#password').blur(function () {
        const password = $('#password').val();
        const passwordConfirm = $('#passwordConfirm').val();
        isValidatedPassword = false;

        $('#password__result').html('');

        const regPassword = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/i;

        if(password.trim() == ''){
            $('#password__result').html('비밀번호를 입력하세요.');
            isValidatedPassword = false;
            return false;
        }

        if(!password.match(regPassword)){
            $('#password__result').html('8~16자로 구성하세요. 숫자, 특수문자가 한 개 이상 포함된 대 소문자를 사용하세요.');
            isValidatedPassword = false;
            return false;
        }

        if(password != passwordConfirm){
            $('#password__result').html('비밀번호가 일치하지 않습니다.');
            isValidatedPassword = false;
            return false;
        }

        isValidatedPassword = true;
    });

    $('#passwordConfirm').blur(function () {
        const password = $('#password').val();
        const passwordConfirm = $('#passwordConfirm').val();

        isValidatedPassword = false;

        $('#password__result').html('');

        const regPassword = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/i;

        if(passwordConfirm.trim() == ''){
            $('#password__result').html('비밀번호를 입력하세요.');
            isValidatedPassword = false;
            return false;
        }

        if(!passwordConfirm.match(regPassword)){
            $('#password__result').html('8~16자로 구성하세요. 숫자, 특수문자가 한 개 이상 포함된 대 소문자를 사용하세요.');
            isValidatedPassword = false;
            return false;
        }

        if(password != passwordConfirm){
            $('#password__result').html('비밀번호가 일치하지 않습니다.');
            isValidatedPassword = false;
            return false;
        }

        isValidatedPassword = true;
    });

    $('#email').blur(function () {
        const email = $('#email').val();
        $('#email__result').html('');

        isValidatedEmail = false;

        const regEmail = /^[\w-\.]{1,25}@([\w-]+\.)+[\w-]{2,4}$/i;

        if(email.trim() == '') {
            $('#email__result').html('이메일 주소를 입력하세요.');
            isValidatedEmail = false;
            return false;
        }

        if(!email.match(regEmail)){
            $('#email__result').html('이메일 주소를 올바르게 입력하세요.');
            isValidatedEmail = false;
            return false;
        }

        $.ajax({
            method: "post",
            url: "http://localhost:8080/members/register/check/email",
            data: {
                email : email
            },
            async: false,
            success: function (result){
                console.log(result);
            },
            error: function (result){
                const response = result.responseJSON;
                console.log(response);
                $('#email__result').html(response.message);
                isValidatedEmail = false;
            }
        })

        isValidatedEmail = true;
    });

    $('#memberName').blur(function () {
        const memberName = $('#memberName').val();
        $('#memberName__result').html('');

        isValidatedName = false;

        const regName = /^[가-힣a-zA-Z]{2,25}$/i;

        if(memberName.trim() == ''){
            $('#memberName__result').html('이름을 입력하세요.');
            isValidatedName = false;
            return false;
        }

        if(!memberName.match(regName)){
            $('#memberName__result').html('2~25자로 구성하세요. 이름을 정확히 입력하세요.');
            isValidatedName = false;
            return false;
        }
        isValidatedName = true;
    });

    $('#birthYear').blur(function () {
        const birthYear = $('#birthYear').val();
        $('#birthYear__result').html('');

        isValidatedBirthYear = false;

        const regBirth = /^(19|20)\d{2}$/

        if(birthYear.trim() == ''){
            $('#birthYear__result').html('생년월일을 입력하세요.');
            isValidatedBirthYear = false;
            return false;
        }

        if(!birthYear.match(regBirth)){
            $('#birthYear__result').html('생년월일을 정확히 입력하세요. 19나 20으로 시작하는 4자 숫자만 가능합니다.');
            isValidatedBirthYear = false;
            return false;
        }

        isValidatedBirthYear = true;
    });

    $('#phoneNo').blur(function () {
        const phoneNo = $('#phoneNo').val();
        $('#phoneNo').val(phoneNo);
        $('#phoneNo__result').html('');

        isValidatedPhoneNo = false;

        const regPhoneNo = /^01(?:0|1|[6-9])-[1-9]\d{2,3}-\d{4}$/

        if(phoneNo.trim() == ''){
            $('#phoneNo__result').html('휴대폰 번호를 입력하세요.');
            isValidatedPhoneNo = false;
            return false;
        }

        if(!phoneNo.match(regPhoneNo)){
            $('#phoneNo__result').html('휴대폰 번호를 정확히 입력하세요. 하이픈(-)도 포함해야 합니다.');
            isValidatedPhoneNo = false;
            return false;
        }

        isValidatedPhoneNo = true;
    });

    $('#register').on('click', function (){
        if(!(isValidatedLoginId && isValidatedPassword && isValidatedEmail && isValidatedName && isValidatedBirthYear && isValidatedPhoneNo)){
            alert('회원가입 양식을 다시 확인해주세요');
            return false;
        }

        const loginId = $('#loginId').val();
        const password = $('#password').val();
        const passwordConfirm = $('#passwordConfirm').val();
        const email = $('#email').val();
        const memberName = $('#memberName').val();
        const birthYear = $('#birthYear').val();
        const gender = $('#gender').val();
        const phoneNo = $('#phoneNo').val();

        $.ajax({
            method: "post",
            url: "http://localhost:8080/members/register",
            data: {
                loginId : loginId,
                password: password,
                passwordConfirm: passwordConfirm,
                email: email,
                memberName: memberName,
                birthYear: birthYear,
                gender : gender,
                phoneNo: phoneNo
            },
            async: false,
            success: function (result){
                console.log(result);
                alert(result.message);
                location.replace("/");
            },
            error: function (result){
                console.log(result);
                const response = result.responseJSON;
                console.log(response);
                $('#loginId__result').html(response.message);
                isValidatedLoginId = false;
            }
        })
    })
})

