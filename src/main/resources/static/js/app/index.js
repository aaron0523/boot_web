/* 브라우저의 스코프는 공용 공간으로 쓰이므로 협업시 중복된 함수 이름이 발생하여 먼저 로딩된 js의 function 에 덮어쓰는 문제 발생
var main 이란 객체를 만들어 해당 객체에서 필요한 모든 function 을 선언해 main 객체 안에서만 function 이 유효하도록 해결
예) var main = {...} 코드를 선언하여 변수의 속성으로 function 을 추가하여 index.js 만의 유효 범위를 만들어 사용 */

let main = {
    init: function () {
        let _this = this; // this 는 var main

        document.querySelector('#btn-save').addEventListener('click', function () {
            _this.save();
        });
        document.querySelector('#btn-update').addEventListener('click', function () {
            _this.update();
        });
        document.querySelector('btn-delete').addEventListener('click', function () {
            _this.delete();
        })
    },

    save: function () {
        let data = {
            title: document.getElementById('title').value,
            author: document.querySelector('#author').value,
            content: document.querySelector('#content').value
        };

        fetch('/api/v1/posts', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(function (response) {
                if (response.ok) {
                    alert('글이 등록되었습니다.');
                    window.location.href = '/';
                } else {
                    throw new Error('글 등록에 실패하였습니다.');
                }
            })
            .catch(function (error) {
                alert(error.message);
            });
    },

    update: function () {
        let data = {
            title: document.querySelector('#title').value,
            content: document.querySelector('#content').value
        }

        let id = document.querySelector('#id').value;

        fetch('/api/v1/posts' + id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }).then(function (response) {
            if (response.ok) {
                alert('글이 수정되었습니다.');
                window.location.href = '/';
            } else {
                throw new Error('글 수정에 실패하였습니다.');
            }
        })
            .catch(function (error) {
                alert(error.message);
            });
    },

    delete: function () {

        let id = document.querySelector('#id').value;

        fetch('/api/v1/posts/'+id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            if(response.ok) {
                alert('글이 삭제되었습니다.');
                window.location.href = '/';
            } else {
                throw new Error('글 삭제에 실패하였습니다.');
            }
        }).catch(function(error) {
            alert(error.message);
        });
    }
};

main.init();